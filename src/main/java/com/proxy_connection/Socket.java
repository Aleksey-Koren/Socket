package com.proxy_connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;

public class Socket {

    private static final String HOST = "localhost";
    private static final Integer PROXY_PORT = 8081;
    private static final Integer LOCAL_PORT = 2000;

    private static java.net.Socket server;


    public static void main(String[] args) {
        try {
            System.out.println("Starting proxy for " + HOST
                    + ":" + PROXY_PORT
                    + " on port " + LOCAL_PORT);

            server = new java.net.Socket(HOST, PROXY_PORT);


            runServer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runServer() throws IOException {
        ServerSocket ss = new ServerSocket(LOCAL_PORT);


        while (true) {
            new MyThread(ss.accept(), server).start();
        }
    }
}

class MyThread extends Thread {

    private final java.net.Socket client;
    private static InputStream serverInputStream;
    private static OutputStream serverOutputStream;

    MyThread(java.net.Socket client, java.net.Socket server) {
        this.client = client;

        try {
            serverInputStream = server.getInputStream();
            serverOutputStream = server.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        final byte[] request = new byte[2048];
        byte[] reply = new byte[2048];

        InputStream streamFromClient = null;
        OutputStream streamToClient = null;

        try {
            streamFromClient = client.getInputStream();
            streamToClient = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }


        int bytesRead;
        try {
            while ((bytesRead = streamFromClient.read(request)) > 0) {

                serverOutputStream.write(request, 0, bytesRead);
                System.out.println("REQUEST: " + new String(request));
                serverOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        int bytesReadResponse;
        try {

            while ((bytesReadResponse = serverInputStream.read(reply)) > 0) {
                streamToClient.write(reply, 0, bytesReadResponse);
                System.err.println("RESPONSE: " + new String(reply));
                streamToClient.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                streamToClient.close();
                streamFromClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
