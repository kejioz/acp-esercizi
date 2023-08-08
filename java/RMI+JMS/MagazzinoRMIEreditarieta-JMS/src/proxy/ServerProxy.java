package proxy;

import java.util.Hashtable;

import javax.naming.*;
import javax.jms.*;

public class ServerProxy {
    
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
            System.out.println("[ServerProxy] Creata Connection");

            //Creo il receiver e gli passo entrambe le code
            ThreadReceiver receiver = new ThreadReceiver(qconn,queueRequest,queueResponse);
            receiver.start();
            
        } catch (JMSException e) {
            System.err.println("[ServerProxy] JMSException: "+e.getMessage());
        } catch (NamingException e){
            System.err.println("[ServerProxy] NamingException");
        }

    }
}
