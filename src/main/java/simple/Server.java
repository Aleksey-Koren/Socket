package simple;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(1010);

            System.out.println("Before accept");
            Socket socket = server.accept();

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("ready to read");
            while (true) {
                byte[] bytes = new byte[20];
                int num = 0;
                int count = 0;
                while (true) {
                    num = in.read(bytes);
                    System.out.println(new String(bytes));
                    out.write(bytes,0, num);
                    count++;
                    if (in.available() < 1) {
                        break;
                    }
                }
                if (count > 0) {
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
