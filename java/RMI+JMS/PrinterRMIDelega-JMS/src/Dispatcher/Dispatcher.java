package Dispatcher;

import java.util.Hashtable;
import javax.jms.*;
import javax.naming.*;

public class Dispatcher {

    public static void main(String[] args) {
        
        //Creo la HashTable per mantenere le properties del provider, dell'initial context, della coda da creare che poi passo al mio contesto
        Hashtable<String,String> prop = new Hashtable<String,String>();
        prop.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        prop.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        prop.put("queue.PrintRequest","PrintRequest");

        try{

            //Passo la Hashtable al mio initial context
            Context initialContext = new InitialContext(prop);

            //Lookup administerdObjects, qcf e destination
            QueueConnectionFactory qcf = (QueueConnectionFactory) initialContext.lookup("QueueConnectionFactory");
            Queue queue = (Queue) initialContext.lookup("PrintRequest");

            //Creo la connection e la starto siccome sono il receiver
            QueueConnection qconn = qcf.createQueueConnection();
            qconn.start();

            //Creo la session
            QueueSession qsession = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creo il receiver
            QueueReceiver qreceiver = qsession.createReceiver(queue);

            //Setto il listener al receiver
            DispatcherListener listener = new DispatcherListener();
            qreceiver.setMessageListener(listener);
            System.out.println("[Dispatcher] Correttamente avviato, listener settato.");

        }catch(NamingException e){
            System.err.println("[Dispatcher] Naming Exception");
        }catch(JMSException e){
            System.err.println("[Dispatcher] JMS Exception");
        }


    


    }
    
}
