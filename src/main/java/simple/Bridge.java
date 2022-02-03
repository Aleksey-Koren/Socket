package simple;

import com.google.common.primitives.Bytes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Bridge {

    public static void main(String[] args) throws UnknownHostException {
        try {
            ServerSocket ss = new ServerSocket(1020);
            Socket client = ss.accept();
            Socket server = new Socket(InetAddress.getLocalHost(), 1010);
            Thread.sleep(2000);
            DataInputStream clientIn = new DataInputStream(client.getInputStream());
            DataInputStream serverIn = new DataInputStream(server.getInputStream());
            DataOutputStream clientOut = new DataOutputStream(client.getOutputStream());
            DataOutputStream serverOut = new DataOutputStream(server.getOutputStream());

            while (true) {
                byte[] bytes = new byte[1000000];
                int num;
                int count = 0;
                List<Byte> bytesList = new ArrayList<>();
                StringBuilder builder = new StringBuilder();
                builder.append("Client -> Server ");
                while (true) {
                    num = clientIn.read(bytes);
                    addToList(bytesList, bytes, num);
                    builder.append(new String(bytes, 0, num));
                    if(num > 0) {
                        count++;
                    }
                    if (clientIn.available() < 1) {
                        break;
                    }
                }
                if (count > 0) {
                    serverOut.write(Bytes.toArray(bytesList));
                    serverOut.flush();
                }
                System.out.println(builder);

                byte[] bytes2 = new byte[1000000];
                int num2;
                int count2 = 0;
                List<Byte> bytesList2 = new ArrayList<>();
                StringBuilder builder2 = new StringBuilder();
                builder2.append("Client <- Server ");
                while (true) {
                    num2 = serverIn.read(bytes2);

                    addToList(bytesList2, bytes2, num2);
                    builder2.append(new String(bytes2, 0, num2));
                    if(num2 > 0) {
                        count2++;
                    }
                    if (serverIn.available() < 1) {
                        break;
                    }
                }
                if (count2 > 0) {
                    clientOut.write(Bytes.toArray(bytesList2));
                    clientOut.flush();
                }
                System.out.println(builder2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void addToList(List<Byte> bytesList, byte[] bytes, int num) {
        for(int i = 0; i < num ; i++) {
            bytesList.add(bytes[i]);
        }
    }
}