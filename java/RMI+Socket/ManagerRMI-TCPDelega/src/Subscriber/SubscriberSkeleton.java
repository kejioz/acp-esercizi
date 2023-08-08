package Subscriber;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Services.ISubscriber;

public class SubscriberSkeleton implements ISubscriber{
    
    private int porto;
    private SubscriberImpl subscriber;

    protected SubscriberSkeleton (int porto,SubscriberImpl subscriber){
        this.porto=porto;
        this.subscriber = subscriber; 
    }

    public void runSkeleton(){

        try{

            ServerSocket serverSocket = new ServerSocket(porto);
            System.out.println("[Skeleton] Skeleton avviato... in ascolto sul porto "+porto);
            
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("[Skeleton] Connessione accettata! Avvio del Thread Worker...");
                SubscriberThread worker = new SubscriberThread( this, clientSocket );
                System.out.println("[Skeleton] Worker avviato");
                worker.run();
                
            }


        }catch(IOException e){
            System.err.println("[Skeleton] "+e.getMessage());
        }

    }

    @Override
    public void notifyAlert(int criticality) {
        
        System.out.println("[SubscriberSkeleton] Ricevuta richiesta di notifyAlert con criticità "+criticality);
        //Effettua semplicemente la call al SubscriberImpl
        subscriber.notifyAlert(criticality);

    }

}
