package simple;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class OneDirectionalThread implements Runnable{

    private Socket from;
    private Socket to;

    public OneDirectionalThread (Socket from, Socket to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        try {
            DataInputStream fromIn = new DataInputStream(from.getInputStream());
            DataOutputStream toOut = new DataOutputStream(to.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
