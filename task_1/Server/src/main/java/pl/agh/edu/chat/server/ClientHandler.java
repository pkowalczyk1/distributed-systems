package pl.agh.edu.chat.server;

import pl.agh.edu.chat.common.Client;
import pl.agh.edu.chat.common.Message;
import pl.agh.edu.chat.common.MessageParser;
import pl.agh.edu.chat.common.MessageType;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable {

    private static final Logger log = Logger.getLogger(ClientHandler.class.getName());

    private final Client client;
    private final ClientsHelper clientsHelper;
    private boolean running = true;

    public ClientHandler(Client client) {
        this.client = client;
        this.clientsHelper = ClientsHelper.getInstance();
    }

    @Override
    public void run() {
        log.info("Opened connection with client " + client.id());
        sendConnectionConfirmation();
        tcpListener();
    }

    private void tcpListener() {
        try {
            while (running) {
                String messageString = client.inStream().readLine();
                Message message = MessageParser.stringToMessage(messageString);
                handleMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeClientConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            log.info("Closed connection with client " + client.id());
        }
    }

    private void handleMessage(Message message) {
        switch (message.messageType()) {
            case DISCONNECT -> {
                clientsHelper.removeClient(client.id());
                running = false;
            }
            case MESSAGE -> sendMessageTo(message.messageContent(), message.receiverId());
        }
    }

    private void sendMessageTo(String content, Integer receiverId) {
        Message message = new Message(MessageType.MESSAGE, content, client.id(), receiverId);
        var clientOptional = clientsHelper.getClient(receiverId);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.outStream().println(MessageParser.messageToString(message));
        } else {
            log.info("No client with id: " + receiverId);
        }
    }

    private void sendConnectionConfirmation() {
        var ids = clientsHelper.getClientsMatchingPredicate(c -> true).stream()
                .map(Client::id)
                .collect(Collectors.toSet());
        String messageContent = client.id() + ":" + ids;
        Message messageToConnected =
                new Message(MessageType.CONNECT, messageContent, -1, client.id());
        client.outStream().println(MessageParser.messageToString(messageToConnected));
        var otherClients =
                clientsHelper.getClientsMatchingPredicate(c -> c.id() != client.id());
        otherClients.forEach(c -> sendIds(c, ids));
    }

    private void sendIds(Client client, Collection<Integer> ids) {
        Message message = new Message(MessageType.MESSAGE, "Connected clients: " + ids, -1, client.id());
        client.outStream().println(MessageParser.messageToString(message));
    }

    private void closeClientConnection() throws IOException {
        client.inStream().close();
        client.outStream().close();
        client.socket().close();
        var clients = clientsHelper.getClientsMatchingPredicate(c -> true);
        var ids = clients.stream()
                .map(Client::id)
                .collect(Collectors.toSet());
        clients.forEach(c -> sendIds(c, ids));
    }
}
