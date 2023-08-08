package Subscriber;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SubscriberSkeleton implements ISubscriber{

    private int porta;
    private DatagramSocket serverSocket;
    private SubscriberImpl subscriber;
    
    public SubscriberSkeleton(int porta,String nomefile){
        this.porta = porta;
        subscriber = new SubscriberImpl(nomefile);
    }

    public void runSkeleton(){

        try{

            serverSocket = new DatagramSocket(porta, InetAddress.getLocalHost());

            while(true){
                byte[] buffer = new byte[1024];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(request);
                System.out.println("[Skeleton] Richiesta ricevuta, avvio il worker");
                SubscriberWorker worker = new SubscriberWorker(this,request);
                worker.start();
            }

        }catch(SocketException e){
            e.printStackTrace();
        }catch(UnknownHostException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void notifyAlert(int criticality) {

         subscriber.notifyAlert(criticality);

    }

}
