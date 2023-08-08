import java.util.Hashtable;
import javax.naming.*;
import javax.jms.*;

//Point to point

public class Publisher {
    
    public static void main(String[] args) {
        
        Hashtable<String,String> prop = new Hashtable<String,String>();

        //Ho creato la Hashtable prop, ora devo associare il provider sul porto 61616 e l'initial context

        prop.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");

        prop.put("java.naming.provider.url","tcp://127.0.0.1:61616"); //associo il provider (in questo caso activemq)
    
        //Ora associo il nome della queue di jndi al nome della coda fisica
        prop.put("queue.test","mytestqueue");

        try {
            //Creo il contesto iniziale
            Context jndiContext = new InitialContext(prop);

            //Effettuo la Lookup degli Administered Objects (Conn Factory e Destination)
            QueueConnectionFactory queueConnFactory = (QueueConnectionFactory) jndiContext.lookup("QueueConnectionFactory");
            Queue queue = (Queue) jndiContext.lookup("test"); //La destination è Queue

            //Creo la Connection
            QueueConnection queueConn = queueConnFactory.createQueueConnection();

            //Creo la Session (single-threaded)
            QueueSession queueSession = queueConn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);

            //Creo il sender
            QueueSender sender = queueSession.createSender(queue);

            //Creo il messaggio che il sender invierà
            TextMessage message = queueSession.createTextMessage();
            for (int i=0;i<5;i++){
                message.setText("hello_"+i);
                sender.send(message); //Sendo
            }
            message.setText("fine"); //setto la fine
            sender.send (message);

            System.out.println("I messaggi sono stati inviati");

            //Cleanup delle risorse
            sender.close();
            queueSession.close();
            queueConn.close();

        } catch (Exception e) {
            System.out.println("Eccezoine : "+e.getMessage());
        }

    }

}
