package client;

import java.util.Hashtable;

import javax.naming.*;
import javax.jms.*;

public class Client {
    
    public static void main(String[] args) {
        
        Hashtable<String,String> prop = new Hashtable<String,String>();

        //Bindo alla Hashtable il ContestoIniziale di Jndi ed il porto dove opera il provider
        prop.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        prop.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");

        //Adesso creo due code, Richiesta e Risposta
        prop.put("queue.Richiesta","Richiesta");
        prop.put("queue.Risposta","Risposta");

        try {

            //Creo il contesto iniziale
            Context ctx = new InitialContext(prop);

            //Ho p2p quindi non uso topic ma queue
            QueueConnectionFactory qconnfactory = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");

            //Creo code
            Queue queueRequest = (Queue) ctx.lookup("Richiesta");
            Queue queueResponse = (Queue) ctx.lookup("Risposta");

            //Creo la connection
            QueueConnection qconn = qconnfactory.createQueueConnection();
            System.out.println("[Client] Creata connection");

            //Adesso creo due thread a cui passo la connection
            ClientThread sender = new ClientThread(qconn,queueRequest,"sender");
            System.out.println("[Client] Creato sender");
            sender.start();
            ClientThread receiver = new ClientThread(qconn,queueResponse,"receiver");
            System.out.println("[Client] Creato receiver");
            receiver.start();


        } catch (NamingException e) {
            System.err.println("[Client] Naming Exception");
        } catch (JMSException e){
            System.err.println("[Client] JMSException");
        }

    }
}
