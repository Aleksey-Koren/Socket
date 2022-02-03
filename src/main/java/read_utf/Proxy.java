package read_utf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Proxy {

    ServerSocket serverSocket;

    {
        try {
            serverSocket = new ServerSocket(1010);
            Socket client = serverSocket.accept();
            Socket server = new Socket(InetAddress.getLocalHost(), 1020);
            DataInputStream clientIn = new DataInputStream(client.getInputStream());
            DataInputStream serverIn = new DataInputStream(server.getInputStream());
            DataOutputStream clientOut = new DataOutputStream(client.getOutputStream());
            DataOutputStream serverOut = new DataOutputStream(server.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
