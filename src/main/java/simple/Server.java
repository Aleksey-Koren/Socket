package simple;

import com.google.common.primitives.Bytes;
import org.apache.commons.lang3.ArrayUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
                int num = 0;
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
                    out.write(Bytes.toArray(bytesList));
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
