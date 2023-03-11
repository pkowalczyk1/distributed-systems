package pl.agh.edu.chat.client;

import org.apache.commons.lang3.StringUtils;
import pl.agh.edu.chat.common.Client;
import pl.agh.edu.chat.common.Message;
import pl.agh.edu.chat.common.MessageParser;
import pl.agh.edu.chat.common.MessageType;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Objects;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;
import static pl.agh.edu.chat.common.MessageType.DISCONNECT;
import static pl.agh.edu.chat.common.MessageType.MESSAGE;

public class MessageSender extends MessageHandler {

    private final Client client;
    private final UdpSocketsHelper udpSocketsHelper;

    public MessageSender(Client client, UdpSocketsHelper udpSocketsHelper) {
        this.client = client;
        this.udpSocketsHelper = udpSocketsHelper;
    }

    @Override
    public void run() {
        Scanner userInput = new Scanner(System.in);
        try {
            while (ClientApp.isClientRunning()) {
                System.out.println(
                        "Give message to send (format: U:<ascii_art_filename> or M:<ascii_art_filename> or " +
                                "<receiver_id>:<message_content> or 'exit' to disconnect):");
                String input = userInput.nextLine();
                handleInput(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleInput(String input) throws IOException {
        if (Objects.equals(input.trim(), "exit")) {
            handleTcpMessage("disconnected", -1, true);
            ClientApp.shutdownClient();
            udpSocketsHelper.closeAllSockets();
            return;
        }

        String[] splitInput = StringUtils.split(input, ":");
        if (StringUtils.equalsAny(splitInput[0], "U", "M")) {
            InputStream is = getClass().getClassLoader().getResourceAsStream(splitInput[1]);
            if (is != null) {
                handleUdpMessage(splitInput[0], new String(is.readAllBytes()));
            } else {
                System.out.println("No such resource");
            }
        } else {
            int receiverId = Integer.parseInt(splitInput[0]);
            String content = splitInput[1];
            handleTcpMessage(content, receiverId, false);
        }
    }

    private void handleUdpMessage(String type, String content) throws IOException {
        int port;
        InetAddress address;
        switch (type) {
            case "U" -> {
                port = ClientApp.SERVER_PORT;
                address = InetAddress.getByName(ClientApp.SERVER_ADDRESS);
            }
            case "M" -> {
                port = ClientApp.MCAST_PORT;
                address = InetAddress.getByName(ClientApp.MCAST_ADDRESS);
            }
            default -> {
                System.out.println("Unknown UDP message type.");
                return;
            }
        }

        Message message = new Message(MESSAGE, content, client.id(), -1);
        byte[] sendBuffer = MessageParser.messageToString(message).getBytes(UTF_8);
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
        client.udpSocket().send(sendPacket);
    }

    private void handleTcpMessage(String content, int receiverId, boolean isExit) {
        MessageType type;
        if (isExit) {
            type = DISCONNECT;
        } else {
            type = MESSAGE;
        }

        Message message = new Message(type, content, client.id(), receiverId);
        client.outStream().println(MessageParser.messageToString(message));
    }
}
