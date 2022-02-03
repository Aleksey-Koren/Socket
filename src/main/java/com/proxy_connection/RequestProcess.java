package com.proxy_connection;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class RequestProcess implements Runnable {

    private final Socket incoming;

    public RequestProcess(Socket incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {

        try  {
            Socket outgoing = new Socket(InetAddress.getLocalHost(), 1001);


            InputStream clientIn = incoming.getInputStream();
            OutputStream clientOut = incoming.getOutputStream();
            InputStream serverIn = outgoing.getInputStream();
            OutputStream serverOut = outgoing.getOutputStream();

            try {
                byte[] buff = new byte[2048];
                int length;
                while ((length = clientIn.read(buff)) > 0) {
                    System.out.println(new String(buff) + Thread.currentThread().getName());
                    serverOut.write(buff, 0, length);
                    serverOut.flush();
                   }
//                clientIn.transferTo(serverOut);
//                System.out.println("Send to server " + Thread.currentThread().getName());

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                byte[] buff1 = new byte[2048];
                int length1;
                while ((length1 = serverIn.read(buff1)) > 0) {
                    System.out.println(new String(buff1) + Thread.currentThread().getName());
                    clientOut.write(buff1, 0, length1);
                    clientOut.flush();
                }
//                serverIn.transferTo(clientOut);
//                System.out.println("Send to client " + Thread.currentThread().getName());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                serverOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                clientOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}