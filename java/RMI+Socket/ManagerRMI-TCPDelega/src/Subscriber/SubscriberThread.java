package Subscriber;

import java.net.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SubscriberThread extends Thread{
    
    private SubscriberSkeleton skeleton;    //per le call
    private Socket clientSocket;
    DataInputStream input;
    DataOutputStream output;

    protected SubscriberThread(SubscriberSkeleton sk, Socket cs){
        this.skeleton=sk;
        this.clientSocket=cs; //per l'outputstream
        try{
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
        }catch(IOException e){
            System.err.println("[SubscriberThread] IOException nel costruttore");
        }
    }

    @Override
    public void run() {
        
        try{

            //Leggo il valore in entrata di criticality
            int criticality = input.readInt();
            System.out.println("[SubscriberThread] Il valore di criticality letto è "+criticality);

            //Effettuo l'upcall allo skeleton
            skeleton.notifyAlert(criticality);
            System.out.println("[SubscriberThread] Upcall allo skeleton effettuata");

            //Messaggio di ack
            output.writeUTF("ack");
            System.out.println("[SubscriberThread] Mandato messaggio di ack");

        }catch(IOException e){
            System.err.println("[SubscriberThread] IOException nel run");
        }

    }

}
