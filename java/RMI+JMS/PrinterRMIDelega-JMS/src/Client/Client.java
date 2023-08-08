package Client;

import java.util.Hashtable;
import java.util.Random;

import javax.naming.*;
import javax.jms.*;

public class Client {
    
    public static void main(String[] args) {
        
        //Creating HashTable to setup JMS
        Hashtable<String,String> prop = new Hashtable<String,String>();

        //Binding provider address and initial context
        prop.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        prop.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");

        //Creo la coda che poi sarà la destination
        prop.put("queue.PrintRequest","PrintRequest");

        try{

            //Creo il contesto iniziale
            Context initialContext = new InitialContext(prop);

            //Lookup administered objects, qcf e destination
            QueueConnectionFactory qcf = (QueueConnectionFactory) initialContext.lookup("QueueConnectionFactory");
            Queue queue = (Queue) initialContext.lookup("PrintRequest");

            //Adesso posso creare la connection
            QueueConnection qconn = qcf.createQueueConnection();

            //Sono il sender quindi non devo startare la connection, creo la session
            QueueSession qsess = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creo il sender
            QueueSender qsender = qsess.createSender(queue);

            //Adesso creo cinque messaggi e li spedisco
            Random rand = new Random();
            String nomePrinter = args[0];
            for (int i=0;i<5;i++){

                MapMessage mapmessage = qsess.createMapMessage();
                String nomeDocumento = ("doc"+rand.nextInt(41));
                mapmessage.setString("nomeDocumento", nomeDocumento);
                mapmessage.setString("nomePrinter", nomePrinter);
                qsender.send(mapmessage);

            }

            //Cleanup risorse
            qsender.close();
            qsess.close();
            qconn.close();            

        }catch(NamingException e){
            System.err.println("[Client] Naming Exception");
        }catch(JMSException e){
            System.err.println("[Client] JMSException");
        }


    }

}
