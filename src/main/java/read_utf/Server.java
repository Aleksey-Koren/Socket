package read_utf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(1010);

            System.out.println("Before accept");
            Socket socket = server.accept();

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while (true) {
                System.out.println("wait to read " + Instant.now());
                String data = in.readUTF();
                out.writeUTF("Server accepted " + data);
//                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
