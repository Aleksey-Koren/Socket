package simple;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BidirectionalBridge {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(1050);
            while (true) {
                Socket client = ss.accept();
                Socket server = new Socket(InetAddress.getByName("localhost"), 3306);
                BridgeThread bridge = new BridgeThread(client);
                new Thread(bridge).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
