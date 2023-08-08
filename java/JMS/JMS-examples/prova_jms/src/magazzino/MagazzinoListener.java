package magazzino;

import coda.*;

import javax.jms.*;

public class MagazzinoListener implements MessageListener {

    private Coda coda;
    private QueueConnection qconn;

    //Al Listener passo la Connection che mi permette di creare le Sessioni! Importante! E poi gli passo la coda
    public MagazzinoListener(Coda coda, QueueConnection qconn){
        this.coda=coda;
        this.qconn=qconn;
    }
    

    @Override
    public void onMessage(Message message) {
    
        //TIPOMESSAGGIO m = (TIPOMESSAGGIO) message; in questo caso io uso MapMEssage
        MapMessage mm = (MapMessage) message;

        //Creo il Thread Magazzino e gli passo la coda, qconn e mapmessage
        //Poi avvio il Thread
        MagazzinoThread mt = new MagazzinoThread(coda, mm, qconn);
        mt.start();
        
    }

}
