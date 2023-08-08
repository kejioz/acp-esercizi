package Subscriber;

import java.net.DatagramPacket;

public class SubscriberThread extends Thread{
    
    private SubscriberSkeleton skeleton;
    private DatagramPacket request;

    protected SubscriberThread(SubscriberSkeleton skeleton, DatagramPacket request){
        this.skeleton=skeleton;
        this.request=request;
        System.out.println("[thread] avviato");
    }

    @Override
    public void run() {
        
        try {
            
            //Leggo la richiesta (che è la criticality da poi upcallare)
            String message = new String(request.getData(),0,request.getLength());
            int criticality = Integer.parseInt(message);

            //Faccio call allo skeleton
            skeleton.notifyAlert(criticality);
            System.out.println("[thread] effettuata upcall allo skeleton di criticality "+criticality);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
