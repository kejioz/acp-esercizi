package Client;

import javax.jms.*;


public class ReceiverThread extends Thread{

    private Queue risposta;
    private QueueConnection qconn;
    
    protected ReceiverThread(QueueConnection qconn,Queue risposta){
        this.risposta=risposta;
        this.qconn=qconn;
    }

    @Override
    public void run() {
        
        try{
            //Sono il receiver quindi devo startare la connection
            qconn.start();

            //Starto la session
            QueueSession qsess = qconn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);

            //Creo il receiver
            QueueReceiver qreceiver = qsess.createReceiver(risposta);

            //Setto il listener
            ReceiverListener listener = new ReceiverListener();
            qreceiver.setMessageListener(listener);

        }catch (JMSException e) {
            System.err.println("JMSException in Thread");
        }

    }
    
}
