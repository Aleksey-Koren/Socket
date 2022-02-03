package simple;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 1010);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String keyboardInput = reader.readLine();
                out.write(keyboardInput.getBytes(StandardCharsets.UTF_8));
                out.flush();

                byte[] bytes = new byte[20];
                int num;
                StringBuilder builder = new StringBuilder();
                while (true) {
                    num = in.read(bytes);

                    builder.append(new String(bytes, 0, num));
                    if (in.available() < 1) {
                        break;
                    }
                }
                System.out.println(builder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}