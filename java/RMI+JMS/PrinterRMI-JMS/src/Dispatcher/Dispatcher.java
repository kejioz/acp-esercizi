package Dispatcher;

import javax.jms.*;
import javax.naming.*;

import java.util.Hashtable;


public class Dispatcher {
    
    public static void main(String[] args) {
        
        try {   

            //Creo HAsh
            Hashtable<String,String> prop = new Hashtable<String,String>();

            //Inserisco properties
            prop.put("java.naming.provider.url","tcp://127.0.0.1:61616");
            prop.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            prop.put("topic.PrintRequest","PrintRequest");

            //Creo contesto
            Context initialContext = new InitialContext(prop);

            //Prendo administered objects
            TopicConnectionFactory tcf = (TopicConnectionFactory) initialContext.lookup("TopicConnectionFactory");
            Topic topic = (Topic) initialContext.lookup("PrintRequest");

            //Creo connection
            TopicConnection tconn = tcf.createTopicConnection();

            //Starto connection siccome sono receiver
            tconn.start();

            //Creo la session
            TopicSession tsess = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creo il subscriber
            TopicSubscriber tsub = tsess.createSubscriber(topic);
            
            //Setto il listener
            DispatcherListener listener = new DispatcherListener();
            tsub.setMessageListener(listener);


        } catch (JMSException | NamingException e) {
            System.out.println(e.getMessage());
        }

    }

}
