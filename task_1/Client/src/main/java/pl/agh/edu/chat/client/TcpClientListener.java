package pl.agh.edu.chat.client;

import pl.agh.edu.chat.common.Client;
import pl.agh.edu.chat.common.Message;
import pl.agh.edu.chat.common.MessageParser;

import java.io.IOException;

public class TcpClientListener extends MessageHandler {

    private final Client client;

    public TcpClientListener(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            while (ClientApp.isClientRunning()) {
                String messageString = client.inStream().readLine();
                Message message = MessageParser.stringToMessage(messageString);
                handleMessage(message, client.id());
            }
            System.out.println("Closed TCP socket");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
