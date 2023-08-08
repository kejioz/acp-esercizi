package Manager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import Service.ISubscriber;

public class SubscriberProxy implements ISubscriber {
    
    private int portaSkeleton;
    private int idComponente;
    private DatagramSocket socket;

    public SubscriberProxy(int portaSkeleton,int idComponente){
        this.portaSkeleton=portaSkeleton;
        this.idComponente=idComponente;
    }

    @Override
    public void notifyAlert(int criticality) {
        
        
        try {

            //Creo socket 
            socket = new DatagramSocket(0);
            System.out.println("[PROXY] SOCKET AVVIATA SU "+socket.getLocalPort());
            //Creo datagram packet da mandare a portaSkeleton
            String message = Integer.toString(criticality);
            DatagramPacket tosend = new DatagramPacket(message.getBytes(),message.length(),InetAddress.getLocalHost(),portaSkeleton);

            //SENDO IL MESSAGGIO
            socket.send(tosend);
            System.out.println("[PROXY] MESSAGGIO CON CRITICALITY "+criticality+" MANDATO CON SUCCESSO");
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public int getIdComponente() {
        return idComponente;
    }

}
