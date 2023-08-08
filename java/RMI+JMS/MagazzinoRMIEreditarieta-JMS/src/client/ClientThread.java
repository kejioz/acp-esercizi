package client;

import java.util.Random;

import javax.jms.*;

public class ClientThread extends Thread {
    
    private QueueConnection qconn;
    private Queue queue;
    String name;

    public ClientThread(QueueConnection qconn,Queue queue,String name){
        super();
        this.qconn=qconn;
        this.queue=queue;
        this.name=name;
    }

    public void run(){

        try{

            if (this.name.compareTo("sender")==0){
                System.out.println("[SenderThread] Instanziato correttamente");

                //Se il thread è sender non devo startare la connection
                //Creo la Session
                QueueSession queueSession = qconn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
                System.out.println("[SenderThread] Creata session");

                //Creo il Sender
                QueueSender queueSender = queueSession.createSender(queue);
                System.out.println("[SenderThread] Creato queueSender");

                //Creo il Message che in questo caso sarà un MapMessage
                MapMessage mapmessage = queueSession.createMapMessage();
                
                //Devo fare 10 messaggi
                for (int i=0;i<3;i++){

                    Random rand = new Random();
                    Random rand2 = new Random();
                    if (rand.nextInt(2)<1){
                        int val=rand2.nextInt(50);
                        mapmessage.setString("richiesta", "deposita");
                        mapmessage.setInt("id_articolo",val);
                        System.out.println("[SenderThread] Inviato messaggio di deposito di "+val);
                    }else{
                        int val=rand2.nextInt(50);
                        mapmessage.setString("richiesta", "preleva");
                        mapmessage.setInt("id_articolo",val);
                        System.out.println("[SenderThread] Inviato messaggio di prelievo di "+val);
                    }
                    queueSender.send(mapmessage);
                }

                //Cleanup
                //queueSender.close();
                //queueSession.close();
            }
            else{

                //Se il thread è receiver devo startare la connection
                qconn.start();
                System.out.println("[ReceiverThread] Connection startata");

                //Creo la session
                QueueSession queueSession = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                System.out.println("[ReceiverThread] Session creata");

                //Creo il receiver
                QueueReceiver queueReceiver = queueSession.createReceiver(queue);
                System.out.println("[ReceiverThread] queueReceiver creato");

                //Creo il listener
                ClientListener listener = new ClientListener();
                System.out.println("[ReceiverThread] Listener creato");
                
                //Setto il listener al receiver
                queueReceiver.setMessageListener(listener);
                System.out.println("[ReceiverThread] Listener settato");

                //Cleanup
                //queueReceiver.close();
                //queueSession.close();

            }

        }catch(JMSException e){
            System.err.println("[ClientThread] JMSException");
        }
    }

}
