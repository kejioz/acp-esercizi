package client;

import javax.naming.*;
import javax.jms.*;

import java.util.Hashtable;
import java.util.Random;

public class Client {
    public static void main(String[] args){

        //Setto dove contattare il provider e dove prendere il contesto inziale
        Hashtable <String,String> p = new Hashtable<String,String>();
        p.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        p.put("java.naming.provider.url","tcp://127.0.0.1:61616");

        //Definisco il nome della coda
        p.put("queue.Risposta","Risposta");

        //Creo il contesto iniziale a valle delle proprietà che ho impostato nella hashtable p
        try {
            Context ctx = new InitialContext(p);
            //Lookup degli Administered Objects cioè ConnectionFactory e Destination
            QueueConnectionFactory qconnf = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory"); //ricorda di fare typecasting
            Queue queueRequest = (Queue) ctx.lookup("Richiesta");
            Queue queueResponse = (Queue) ctx.lookup("Risposta");

            //Creo la connessione
            QueueConnection qconn = qconnf.createQueueConnection();
            qconn.start(); //Lo devo fare siccome sono nel Consumer, se fossi nel Producer non dovrei farlo

            //Creo la Sessione
            QueueSession qsession = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creo il Receiver
            QueueReceiver qreceiver = qsession.createReceiver(queueResponse);

            //Creo il Listener per il receiver
            ClientListener listener = new ClientListener();

            //Setto il listener per il receiver
            qreceiver.setMessageListener(listener);

            //Creo il sender
            QueueSender qsender = qsession.createSender(queueRequest);

            MapMessage message = qsession.createMapMessage();

            for (int i=0;i<10;i++){

                if (Math.random()<0.5){

                    //Caso Deposito
                    message.setString("operazione","deposita");
                    Random r = new Random();
                    int randomvalue = r.nextInt(100);
                    message.setInt("valore",randomvalue);

                    //Setto il JMSReplyTo sulla coda Risposta. Quindi il client è in attesa sulla coda di risposta
                    message.setJMSReplyTo(queueResponse);

                    //Mando il messaggio
                    qsender.send(message);
                    System.out.println("[Client] Inviato messaggio "+" con header "+message.getJMSReplyTo());

                }else{

                    //Caso Prelievo
                    message.setString("operazione","preleva");
                    message.setJMSReplyTo(queueResponse);
                    qsender.send(message);
                    System.out.println("[Client] Inviato messaggio "+" con header "+message.getJMSReplyTo());
                }
            }

        }catch(JMSException e){
            System.err.println("[Client] Eccezione JMS");
        }catch(NamingException e){
            System.err.println("[Client] Exception NamingException");
        }
    }

}
