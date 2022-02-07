package com.bidirectional;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BidirectionalBridge {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(1050);
            while (true) {
                Socket client = ss.accept();
                BidirectionalConnection bridge = new BidirectionalConnection(client);
                new Thread(bridge).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}