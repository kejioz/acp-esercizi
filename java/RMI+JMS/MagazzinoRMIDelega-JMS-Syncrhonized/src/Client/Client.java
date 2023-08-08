package Client;

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.*;


public class Client {
    
    public static void main(String[] args) {
        
        //Gestisco JMS
        Hashtable <String,String> prop = new Hashtable <String,String>();

        //Inserisco nella hashtable le proprietà di activemq
        prop.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        prop.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        prop.put("queue.Richiesta","Richiesta");
        prop.put("queue.Risposta","Risposta");

        try {
            //Caricato il contesto dalla mia hasthable
            Context ctx = new InitialContext(prop);
            
            //Prendo administered objects
            QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");
            Queue richiesta = (Queue) ctx.lookup("Richiesta");
            Queue risposta = (Queue) ctx.lookup("Risposta");

            //Creo connection 
            QueueConnection qconn = qcf.createQueueConnection();

            //Avvio i thread a cui passo la connection
            SenderThread sender = new SenderThread(qconn, richiesta);
            sender.start();
            ReceiverThread receiver = new ReceiverThread(qconn,risposta);
            receiver.start();
            
            
        } catch (JMSException e) {
            System.err.println("JMSException in Client");
        } catch (NamingException e){
            System.err.println("Naming Exception in Client");
        }
    
    }

}
