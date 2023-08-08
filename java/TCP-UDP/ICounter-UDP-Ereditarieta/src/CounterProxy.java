import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;

import javax.xml.crypto.Data;

public class CounterProxy implements ICounter {
    //dichiaro il datagramsocket
    private DatagramSocket socket;

    //costruttore che inizializza il socket.
    public CounterProxy (){
        try {
            socket= new DatagramSocket();
        } catch (SocketException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    //adesso implemento i metodi siccome questo è il proxy lato client

    public void setCount(String id, int s){
        //nota: utilizzo # come separatori per il parsing; creo un nuovo messaggio che mi dice chi ha settato il set count e a quanto
        String message = new String ("setCount#"+id+"#"+s+"#");

        //creo un pacchetto di richiesta e lo mando col socket
        try {
            DatagramPacket request = new DatagramPacket(message.getBytes(),message.getBytes().length,InetAddress.getLocalHost(),9000);
            socket.send(request);
        //creo un pacchetto di risposta e lo ricevo
            byte[] buffer = new byte[100];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);
        } catch (IOException e) {
            // TODO: handle exception
        }

    }

    public int sum(int s){
        int x=0;

        String message = new String ("sum#"+s+"#");

        try {
            //creo la richiesta e la mando
            DatagramPacket request = new DatagramPacket(message.getBytes(), message.getBytes().length, InetAddress.getLocalHost(), 9000);
            socket.send(request);

            //creo la risposta
            byte[] buffer = new byte[100];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);

            //adesso voglio mettermi nella x la risposta quindi creo stringa e parso in int
            String replyMessage = new String (reply.getData(),0, reply.getLength());
            x = Integer.valueOf(replyMessage).intValue();

        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;
    }

    public int increment(){
        int x=0;

        String message=new String("increment#");

        try {
            DatagramPacket request = new DatagramPacket(message.getBytes(), message.getBytes().length, InetAddress.getLocalHost(), 9000);
            socket.send(request);

            byte[] buffer= new byte[100];

            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);

            String replyMessage = new String(reply.getData(),0,reply.getLength());
            x=Integer.valueOf(replyMessage).intValue();
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;
    }

}