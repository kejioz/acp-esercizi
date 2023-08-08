package proxy;

import magazzino.*;

import java.rmi.RemoteException;

import javax.jms.*;

public class ThreadSender extends Thread {
    
    private Queue queueResponse;
    private QueueConnection qconn;
    private Magazzino magazzino;
    private int id_articolo;

    public ThreadSender(Queue queueResponse,Magazzino magazzino, int val, QueueConnection qconn){
        this.queueResponse=queueResponse;
        this.qconn=qconn;
        this.magazzino=magazzino;
        this.id_articolo=val;
        System.out.println("[ThreadSender] Correttamente instanziato");
    }   

    @Override
    public void run() {

        try{
            //Devo chiamare la preleva dal server e mandarlo alla coda di risposta
            id_articolo=magazzino.preleva();
            System.out.println("[ThreadSender] Articolo prelevato dal magazzino: "+id_articolo);

            //Devo mandarlo alla coda di risposta
        
            //Creo session
            QueueSession qsess = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            System.out.println("[ThreadSender] Session creata");

            //Creo sender
            QueueSender qsender = qsess.createSender(queueResponse);
            System.out.println("[ThreadSender] Creato queueSender");

            //Creo il message
            MapMessage mapmessage = qsess.createMapMessage();
            mapmessage.setString("richiesta", "preleva");
            mapmessage.setInt("id_articolo",id_articolo);
            System.out.println("[ThreadSender] Creato messaggio di "+mapmessage.getString("richiesta")+" di "+mapmessage.getInt("id_articolo"));

            //Mando il message
            qsender.send(mapmessage);
            System.out.println("[ThreadSender] Spedito messaggio di "+mapmessage.getString("richiesta")+" di "+mapmessage.getInt("id_articolo"));

            //Cleanup
            qsender.close();
            qsess.close();

        }catch(JMSException e){
            System.err.println("[ThreadSender] JMSException");
        }catch(RemoteException e){
            System.err.println("[ThreadSender] Remote Exception");
        }

    }

}
