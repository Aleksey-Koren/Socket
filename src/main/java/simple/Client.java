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
            byte[] buffer = new byte[256];
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String keyboardInput = reader.readLine();
                out.write(keyboardInput.getBytes(StandardCharsets.UTF_8));
                out.flush();

                Thread.sleep(5000);

                byte[] bytes = new byte[2000];
                int num;
                String str = "";
                while (true) {
                    num = in.read(bytes);
                    str = str + new String(bytes, 0, num);
                    if (in.available() < 1) {
                        break;
                    }
                }
                System.out.println(str);
//                int length = in.read(buffer);
//                System.out.println(new String(buffer, 0, length));
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
