package com.bidirectional;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Supplier;

public class BidirectionalConnection implements Runnable{

    private final Socket client;

    public BidirectionalConnection(Socket client) {
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

            FuncInterface function = () -> {
                closeQuietly(client);
                closeQuietly(server);
            };

            Thread clientToServer = new Thread(new OneThread("[Client -> Server] ", clientIn, serverOut, function));
            Thread serverToClient = new Thread(new OneThread("[Server -> Client]", serverIn, clientOut, function));

            clientToServer.start();
            serverToClient.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeQuietly(Closeable closable) {
        try {
            closable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
