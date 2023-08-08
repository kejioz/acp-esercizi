package Client;

import javax.jms.*;
import javax.naming.*;
import java.util.Hashtable;

public class Client {

    public static void main(String[] args) {

        int dato = Integer.parseInt(args[0]);
        int porto = Integer.parseInt(args[1]);

        if (dato<0 || dato>100){
            System.err.println("[Client] Errore! Il dato deve essere compreso tra 0 e 100");
            return;
        }
        
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

        //Creo la session
        TopicSession tsess = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        //Creo il publisher
        TopicPublisher tpublisher = tsess.createPublisher(topic);

        //Creo mapmessage
        MapMessage mapmessage = tsess.createMapMessage();
        mapmessage.setInt("dato", dato);
        mapmessage.setInt("porto",porto);
        System.out.println("[Client] Mandato mapmessage al topic Richiesta con dato "+dato+" e porto "+porto);

        //Mando il message
        tpublisher.publish(topic, mapmessage);

        }catch(JMSException e){
            System.err.println("[Disk] Errore JMS");
        }catch(NamingException e){
            System.err.println("[Disk] Errore naming");
        }

    }
    
}
