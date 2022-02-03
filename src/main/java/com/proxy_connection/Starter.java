package com.proxy_connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Starter {

    private static Socket socket;
    private static ServerSocket serverSocket;
    public static int count;

    public static void main(String[] args) throws IOException {

        try {
            serverSocket = new ServerSocket(1000);

            while(true) {
                Socket incoming = serverSocket.accept();
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                count++;
                String name = "Thread number " + count;
                Thread thread = new Thread(new RequestProcess(incoming), name);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            serverSocket.close();
        }
    }
}