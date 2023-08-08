package Client;

import java.util.Random;

import javax.jms.*;


public class SenderThread extends Thread{
    
    private Queue richiesta;
    private QueueConnection qconn;

    protected SenderThread(QueueConnection qconn,Queue richiesta){
        this.qconn=qconn;
        this.richiesta=richiesta;
    }

    @Override
    public void run() {
        
        try{
            //Sono il sender quindi non devo startare la connection , posso creare la session
            QueueSession qsess = qconn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);

            //Creo il sender
            QueueSender qsender = qsess.createSender(richiesta);

            //Creo il messaggio
            Random rand = new Random();
            for (int i=0;i<10;i++){

                MapMessage message = qsess.createMapMessage();

                if (Math.random()<0.5){
                    message.setString("tipoRichiesta","deposita");
                    message.setInt("idArticolo", rand.nextInt(50));
                }else{
                    message.setString("tipoRichiesta","preleva");
                }

                qsender.send(message);
                System.out.println("[SenderThread] Richiesta mandata ");

            }

        }catch (JMSException e) {
            System.err.println("JMSException in Thread");
        }
        
    }

}
