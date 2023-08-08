package Subscriber;

import java.net.DatagramPacket;


public class SubscriberWorker extends Thread {
    
    private SubscriberSkeleton skeleton;
    private DatagramPacket rq;

    public SubscriberWorker(SubscriberSkeleton sk,DatagramPacket req){
        this.skeleton=sk;
        this.rq=req;
        System.out.println("[Worker] Avviato");
    }

    @Override
    public void run() {
        
        //Estraggo il valore di criticality
        String criticalityStringa = new String(rq.getData(),0,rq.getLength());
        int criticality = Integer.parseInt(criticalityStringa);

        //Effettuo l'upcall
        skeleton.notifyAlert(criticality);
        System.out.println("[Worker] Upcall per criticality "+criticality+" effettuata");

    }


}
