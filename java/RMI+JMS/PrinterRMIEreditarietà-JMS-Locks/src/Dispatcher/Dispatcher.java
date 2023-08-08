package Dispatcher;

import java.util.Hashtable;

import javax.naming.*;
import javax.jms.*;

public class Dispatcher {
    
    public static void main(String[] args) {
        
        //HashMap
        Hashtable<String,String> prop = new Hashtable<String,String>();
        
        //Mi importo l'initial context ed il provider
        prop.put("java.naming.provider.url", "tcp://127.0.0.1:61616");
        prop.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        //Metto topic
        prop.put("topic.PrintRequest","PrintRequest");

        try{
            //Prendo il contesto
            Context initialContext = new InitialContext(prop);

            //Prendo connectionfactory
            TopicConnectionFactory tcf = (TopicConnectionFactory) initialContext.lookup("TopicConnectionFactory");

            //Prendo topic
            Topic topic = (Topic) initialContext.lookup("PrintRequest");

            //Creo connection e la starto siccome ricevo messaggi
            TopicConnection tc = tcf.createTopicConnection();
            tc.start();
            

            //Creo session aggi
            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creo subscriber
            TopicSubscriber subscriber = ts.createSubscriber(topic);

            //Setto listener
            DispatcherListener listener = new DispatcherListener();
            subscriber.setMessageListener(listener);
            System.out.println("[Dispatcher] Listener settato");

        }catch(Exception e){

        }



    }

}
