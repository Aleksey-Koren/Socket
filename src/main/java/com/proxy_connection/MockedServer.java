package com.proxy_connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MockedServer {


    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1001);
        while(true) {
            Socket accept = server.accept();
            InputStream inputStream = accept.getInputStream();
            OutputStream outputStream = accept.getOutputStream();
            outputStream.write("You have reached the bottom".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        }
    }
}
