package Subscriber;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import Service.ISubscriber;

public class SubscriberSkeleton implements ISubscriber {
    
    private SubscriberImpl subscriber;
    private DatagramSocket socket;
    private int idComponente;
    private int porta;
    private String nomefile;

    public SubscriberSkeleton(int idComponente,int porta, String nomefile){

        this.idComponente = idComponente;
        this.porta = porta;
        this.nomefile = nomefile;
        subscriber = new SubscriberImpl(nomefile);

    }

    public void runSkeleton(){

        try {

            //Avvio la socket
            socket = new DatagramSocket(porta, InetAddress.getLocalHost());
            System.out.println("[SUBSCRIBER-SERVER] IN ASCOLTO SUL PORTO "+porta);
            while(true){

                //Creo il datagrampacket
                byte[] buffer = new byte[1024];
                DatagramPacket request = new DatagramPacket(buffer,buffer.length);

                //Ricevo i pacchetti e avvio il thread
                socket.receive(request);
                SubscriberThread worker = new SubscriberThread(this,request);
                worker.start();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void notifyAlert(int criticality) {
        subscriber.notifyAlert(criticality);
    }

    public int getIdComponente() {
        return idComponente;
    }

}
