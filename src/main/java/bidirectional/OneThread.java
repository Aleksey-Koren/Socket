package bidirectional;

import com.google.common.primitives.Bytes;
import simple.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OneThread implements Runnable {

    private String outputPrefix;
    private DataInputStream in;
    private DataOutputStream out;

    public OneThread(String outputPrefix, DataInputStream in, DataOutputStream out) {
        this.outputPrefix = outputPrefix;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {

        try {
            boolean isStoped = false;
            while (!isStoped) {
                byte[] bytes = new byte[1000000];
                int num;
                int count = 0;
                List<Byte> bytesList = new ArrayList<>();
                StringBuilder builder = new StringBuilder();
                builder.append(outputPrefix);
                while (true) {
                    num = in.read(bytes);
                    if (num == -1) {
                        isStoped = true;
                        break;
                    }
                    Utils.addToList(bytesList, bytes, num);
                    builder.append(new String(bytes, 0, num));
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
                System.out.println(builder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
