package Disk;

import java.util.Hashtable;
import javax.jms.*;
import javax.naming.*;

public class Disk {
    
    public static void main(String[] args) {
        
        //Hashtable per l'initial context
        Hashtable<String,String> prop = new Hashtable<String,String>();

        //Linko provider e roba
        prop.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        prop.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        prop.put("topic.Storage","Storage");

        try{

        //Creo contesto
        Context initialContext = new InitialContext(prop);

        //Importo gli administered objects
        TopicConnectionFactory tcf = (TopicConnectionFactory) initialContext.lookup("TopicConnectionFactory");
        Topic topic = (Topic) initialContext.lookup("Storage");

        //Creo la connection
        TopicConnection tconn = tcf.createTopicConnection();
        tconn.start();

        //Starto la session
        TopicSession tsess = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        //Creo il subscriber
        TopicSubscriber subscriber = tsess.createSubscriber(topic);

        //Creo il listener
        DiskListener listener = new DiskListener(tconn);

        //Setto il listener
        subscriber.setMessageListener(listener);
        System.out.println("[Disk] Listener settato");

        }catch(JMSException e){
            System.err.println("[Disk] Errore JMS");
        }catch(NamingException e){
            System.err.println("[Disk] Errore naming");
        }


    }

}
