package pl.agh.edu.chat.client;

import pl.agh.edu.chat.common.Message;
import pl.agh.edu.chat.common.MessageParser;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpClientListener extends MessageHandler {

    private final DatagramSocket udpSocket;
    private final int clientId;

    public UdpClientListener(DatagramSocket udpSocket, int clientId) {
        this.udpSocket = udpSocket;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try {
            byte[] receiveBuffer = new byte[2048];
            while (ClientApp.isClientRunning()) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                udpSocket.receive(receivePacket);
                Message message = MessageParser.stringToMessage(new String(receivePacket.getData()));
                handleMessage(message, clientId);
            }
        } catch (IOException e) {
            if (udpSocket.isClosed()) {
                System.out.println("Closed UDP socket");
            } else {
                e.printStackTrace();
            }
        }
    }
}
