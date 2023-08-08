package Manager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import Subscriber.ISubscriber;

public class SubscriberProxy implements ISubscriber{

    private int porta;
    private int componentID;
    private DatagramSocket socket;
    
    public SubscriberProxy(int porta,int componentID){

        this.porta=porta;
        this.componentID = componentID;
        try{
            socket = new DatagramSocket(0);
            System.out.println("[Proxy] Avviato, devo contattare lo skeleton su porta "+porta);
        }catch(SocketException e){
            e.printStackTrace();
        }
    }

    @Override
    public void notifyAlert(int criticality) {
        
        try{
            String stringCriticality = Integer.toString(criticality);
            DatagramPacket richiesta = new DatagramPacket(stringCriticality.getBytes(),stringCriticality.length(),InetAddress.getLocalHost(),porta);
            socket.send(richiesta);
            System.out.println("[Proxy] Richiesta mandata con successo!");
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }

    public int getComponentID() {
        return componentID;
    }

    public int getPorta() {
        return porta;
    }

}
