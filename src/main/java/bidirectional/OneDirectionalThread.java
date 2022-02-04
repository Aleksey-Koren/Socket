package bidirectional;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class OneDirectionalThread implements Runnable{

    private Socket client;

    public OneDirectionalThread (Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            Socket server = new Socket("localhost", 8081);
            DataInputStream clientIn = new DataInputStream(client.getInputStream());
            DataOutputStream serverOut = new DataOutputStream(server.getOutputStream());
            DataInputStream serverIn = new DataInputStream(server.getInputStream());
            DataOutputStream clientOut = new DataOutputStream(client.getOutputStream());

            Thread clientToServer = new Thread(new OneThread("[Client -> Server] ", clientIn, serverOut));
            Thread serverToClient = new Thread(new OneThread("[Server -> Client]", serverIn, clientOut));

            clientToServer.start();
            serverToClient.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
