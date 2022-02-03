package simple;

import com.google.common.primitives.Bytes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
                int num;
                int count = 0;
                List<Byte> bytesList = new ArrayList<>();
                while (true) {
                    num = in.read(bytes);
                    addToList(bytesList, bytes, num);
                    System.out.println(new String(bytes, 0 , num));
                    if(num > 0) {
                        count++;
                    }
                    if (in.available() < 1) {
                        break;
                    }
                }
                if (count > 0) {
                    String responce = "RESPONSE FROM SERVER: " + new String(Bytes.toArray(bytesList));
                    out.write(responce.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addToList(List<Byte> bytesList, byte[] bytes, int num) {
        for(int i = 0; i < num ; i++) {
            bytesList.add(bytes[i]);
        }
    }
}
