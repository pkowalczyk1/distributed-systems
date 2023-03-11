package pl.agh.edu.chat.client;

import org.apache.commons.lang3.StringUtils;
import pl.agh.edu.chat.common.Client;
import pl.agh.edu.chat.common.Message;
import pl.agh.edu.chat.common.MessageParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import static pl.agh.edu.chat.common.MessageType.CONNECT;

public class ClientApp {

    static final String SERVER_ADDRESS = "localhost";
    static final String MCAST_ADDRESS = "230.0.0.1";
    static final int SERVER_PORT = 8080;
    static final int MCAST_PORT = 9000;
    private static final AtomicBoolean isClientRunning = new AtomicBoolean(true);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Client started. Waiting for server connection...");
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DatagramSocket udpSocket = new DatagramSocket(socket.getLocalPort());
                DatagramSocket multicastSocket = new MulticastSocket(MCAST_PORT)) {
            BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outStream = new PrintWriter(socket.getOutputStream(), true);

            InetSocketAddress multicastGroup =
                    new InetSocketAddress(InetAddress.getByName(MCAST_ADDRESS), MCAST_PORT);
            NetworkInterface netInt = NetworkInterface.getByName("bge0");
            multicastSocket.joinGroup(multicastGroup, netInt);

            UdpSocketsHelper udpSocketsHelper = new UdpSocketsHelper(udpSocket, multicastSocket);

            int clientId = awaitConnectionConfirmation(inStream);
            Client client = new Client(clientId, outStream, inStream, socket, udpSocket);

            TcpClientListener tcpClientListener = new TcpClientListener(client);
            UdpClientListener udpClientListener = new UdpClientListener(udpSocket, clientId);
            UdpClientListener multicastListener = new UdpClientListener(multicastSocket, clientId);
            MessageSender messageSender = new MessageSender(client, udpSocketsHelper);

            Thread tcpThread = new Thread(tcpClientListener);
            Thread udpThread = new Thread(udpClientListener);
            Thread multicastThread = new Thread(multicastListener);
            Thread messageSenderThread = new Thread(messageSender);

            tcpThread.start();
            udpThread.start();
            multicastThread.start();
            messageSenderThread.start();

            tcpThread.join();
            udpThread.join();
            multicastThread.join();
            messageSenderThread.join();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isClientRunning() {
        return isClientRunning.get();
    }

    public static void shutdownClient() {
        isClientRunning.set(false);
    }

    private static int awaitConnectionConfirmation(BufferedReader inStream) throws IOException {
        String messageString = inStream.readLine();
        Message message = MessageParser.stringToMessage(messageString);
        while (!CONNECT.equals(message.messageType())) {
            messageString = inStream.readLine();
            message = MessageParser.stringToMessage(messageString);
        }

        String[] splitMessage = StringUtils.split(message.messageContent(), ":");
        int clientId = Integer.parseInt(splitMessage[0]);
        System.out.println("Connected. Your id: " + clientId + ". Connected clients: " + splitMessage[1]);

        return clientId;
    }
}
