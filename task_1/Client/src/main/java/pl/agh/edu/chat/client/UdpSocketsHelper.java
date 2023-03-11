package pl.agh.edu.chat.client;

import java.net.DatagramSocket;

public class UdpSocketsHelper {

    private final DatagramSocket udpSocket;
    private final DatagramSocket multicastSocket;

    public UdpSocketsHelper(DatagramSocket udpSocket, DatagramSocket multicastSocket) {
        this.udpSocket = udpSocket;
        this.multicastSocket = multicastSocket;
    }

    public void closeAllSockets() {
        udpSocket.close();
        multicastSocket.close();
    }
}
