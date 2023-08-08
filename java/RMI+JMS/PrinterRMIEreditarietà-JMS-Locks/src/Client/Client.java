package Client;

import java.util.Hashtable;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



public class Client {
    
    public static void main(String[] args) {
        
        Hashtable <String,String> prop = new Hashtable<String,String>();
        prop.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        prop.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        prop.put("topic.PrintRequest","PrintRequest");

        try{

            Context initialContext = new InitialContext(prop);

            TopicConnectionFactory tcf = (TopicConnectionFactory) initialContext.lookup("TopicConnectionFactory");

            Topic topic = (Topic) initialContext.lookup("PrintRequest");

            TopicConnection tc = tcf.createTopicConnection();

            TopicSession ts = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            TopicPublisher tp = ts.createPublisher(topic);

            //Genero i messaggi
            Random rand = new Random();
            String nomePrinter = args[0];
            for (int i=0;i<5;i++){
                MapMessage message = ts.createMapMessage();
                message.setString("nomeDocumento", ("doc"+Integer.toString(rand.nextInt(41))));
                message.setString("nomePrinter",nomePrinter);
                tp.publish(message);
                System.out.println("[Client] Il publisher ha pubblicato un messaggio");
            }

            


        }catch(NamingException e){
            System.err.println("[Client] NamingException");
        }catch(JMSException e){
            System.err.println("[Client] JMSException");
        }


    }

}
