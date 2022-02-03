package read_utf;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 1010);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String keyboardInput = reader.readLine();
                out.writeUTF(keyboardInput);
//                out.flush();
                String dataFromServer = in.readUTF();
                System.out.println(dataFromServer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
