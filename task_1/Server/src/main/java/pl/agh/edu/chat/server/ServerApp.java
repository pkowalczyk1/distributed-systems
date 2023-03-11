package pl.agh.edu.chat.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class ServerApp {

    static final int PORT_NUMBER = 8080;

    private static final Logger log = Logger.getLogger(ServerApp.class.getName());

    public static void main(String[] args) throws InterruptedException {
        log.info("Server starting");
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
                DatagramSocket udpSocket = new DatagramSocket(PORT_NUMBER)) {
            TcpListener tcpListener = new TcpListener(serverSocket);
            UdpListener udpListener = new UdpListener(udpSocket);
            Thread tcpThread = new Thread(tcpListener);
            Thread udpThread = new Thread(udpListener);
            tcpThread.start();
            udpThread.start();
            tcpThread.join();
            udpThread.join();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
