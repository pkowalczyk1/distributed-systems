package pl.agh.edu.chat.server;

import pl.agh.edu.chat.common.Client;
import pl.agh.edu.chat.common.Message;
import pl.agh.edu.chat.common.MessageParser;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

public class UdpListener implements Runnable {

    private final DatagramSocket udpSocket;
    private final ClientsHelper clientsHelper = ClientsHelper.getInstance();

    public UdpListener(DatagramSocket udpSocket) {
        this.udpSocket = udpSocket;
    }

    @Override
    public void run() {
        try {
            byte[] receiveBuffer = new byte[2048];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                udpSocket.receive(receivePacket);
                Message message = MessageParser.stringToMessage(new String(receivePacket.getData(), UTF_8));
                sendToAll(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToAll(Message message) throws IOException {
        var clients = clientsHelper.getClientsMatchingPredicate(client -> client.id() != message.senderId());
        for (Client client : clients) {
            byte[] sendBuffer = MessageParser.messageToString(message).getBytes(UTF_8);
            Socket clientSocket = client.socket();
            DatagramPacket sendPacket =
                    new DatagramPacket(sendBuffer, sendBuffer.length, clientSocket.getInetAddress(),
                            clientSocket.getPort());
            udpSocket.send(sendPacket);
        }
    }
}
