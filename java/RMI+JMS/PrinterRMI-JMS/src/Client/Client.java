package Client;

import javax.jms.*;
import javax.naming.*;

import java.util.Hashtable;
import java.util.Random;


public class Client {
    
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

            //Creo la session
            TopicSession tsess = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creo il publisher
            TopicPublisher tpub = tsess.createPublisher(topic);

            //Devo creare 5 map message
            for (int i=0;i<5;i++){

                MapMessage message = tsess.createMapMessage();

                message.setString("nomeDocumento",("doc"+Integer.toString(new Random().nextInt(40)+1)));
                message.setString("nomePrinter",args[0]);

                tpub.publish(message);
                System.out.println("[CLIENT] Messaggio "+i+" pubblicato su PrintRequest");
            }

            //Cleanup
            tpub.close();
            tsess.close();
            tconn.close();

        } catch (JMSException | NamingException e) {
            System.out.println(e.getMessage());
        }

    }

}
