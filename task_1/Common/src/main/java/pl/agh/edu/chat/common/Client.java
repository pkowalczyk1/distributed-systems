package pl.agh.edu.chat.common;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.Socket;

public record Client(int id, PrintWriter outStream, BufferedReader inStream, Socket socket, DatagramSocket udpSocket) {
}
