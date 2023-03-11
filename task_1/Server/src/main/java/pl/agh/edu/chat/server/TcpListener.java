package pl.agh.edu.chat.server;

import pl.agh.edu.chat.common.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpListener implements Runnable {

    private static final int MAX_CONNECTIONS = 3;
    private final ExecutorService executorService = Executors.newFixedThreadPool(MAX_CONNECTIONS);
    private final ClientsHelper clientsHelper = ClientsHelper.getInstance();
    private final ServerSocket serverSocket;
    private int currentId = 0;

    public TcpListener(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                currentId++;
                Client client = createClient(clientSocket);
                clientsHelper.putClient(client);
                ClientHandler clientHandler = new ClientHandler(client);
                executorService.submit(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Client createClient(Socket clientSocket) throws IOException {
        BufferedReader inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter outStream = new PrintWriter(clientSocket.getOutputStream(), true);
        return new Client(currentId, outStream, inStream, clientSocket, null);
    }
}
