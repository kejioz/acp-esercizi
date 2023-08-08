import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.StringTokenizer;

import javax.xml.crypto.Data;

import org.w3c.dom.css.Counter;

public class CounterWorker extends Thread{
    private DatagramSocket socket;
    private DatagramPacket request;
    private ICounter skeleton;

    public CounterWorker (DatagramSocket s, DatagramPacket r, ICounter sk){
        socket = s;
        request = r;
        skeleton = sk;
    }

    public void run(){
        String message = new String (request.getData(),0,request.getLength());

        System.out.println("\n[CounterWorker] Processing packet:\n"+"-- request size = "+request.getLength()+"\n"+"-- message = "+ message);
        StringTokenizer messageTokens = new StringTokenizer (message, "#");
        String method = messageTokens.nextToken();

        //if section

        if (method.compareTo("setCount") == 0){
            String id = messageTokens.nextToken();
            int x = Integer.valueOf(messageTokens.nextToken()).intValue();
            skeleton.setCount(id, x);

            String replyMessage = "ack";
            DatagramPacket reply = new DatagramPacket(replyMessage.getBytes(), replyMessage.getBytes().length, request.getAddress(), request.getPort());

            try {
                socket.send(reply);
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        else if (method.compareTo("sum") == 0){
            int x = Integer.valueOf(messageTokens.nextToken()).intValue();
            int res = skeleton.sum(x);

            String replyMessage = Integer.toString(res);

            DatagramPacket reply = new DatagramPacket(replyMessage.getBytes(), replyMessage.getBytes().length, request.getAddress(), request.getPort());

            try {
                socket.send(reply);
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }

        else if (method.compareTo("increment") == 0){
            int res = skeleton.increment();

            String replyMessage = Integer.toString(res);
            
            DatagramPacket reply = new DatagramPacket(replyMessage.getBytes(), replyMessage.getBytes().length, request.getAddress(), request.getPort());

            try {
                socket.send(reply);
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        else{
            System.out.println("BAD METHOD");
        }

    }
}
