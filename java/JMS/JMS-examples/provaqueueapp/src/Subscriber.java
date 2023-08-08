import java.util.Hashtable;
import javax.jms.*;
import javax.naming.*;

//Point to point

public class Subscriber {
    
    public static void main(String[] args) {
        
        //Creo HashTable per il naming
        Hashtable <String,String> prop = new Hashtable<String,String>();
        //Lego alla hashtable il provider e l'initial context
        prop.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        prop.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        prop.put("queue.test","mytestqueue"); //Il prefisso queue. non fa parte del nome jndi, lo è solo test

        try {

            Context jndiContext = new InitialContext(prop); //Passo la mia HashTable che quindi è il mio InitialContext
            //Adesso posso effettuare operazioni di Lookup sul contesto. Inizio con gli administered Object
            QueueConnectionFactory queueConnFactory = (QueueConnectionFactory) jndiContext.lookup("QueueConnectionFactory");
            Queue queue = (Queue) jndiContext.lookup("test"); //La mia destination è queue (il prefisso queue. non fa parte del nome jndi)

            //Creo la Connection
            QueueConnection queueConn = queueConnFactory.createQueueConnection();
            //QUI CAMBIA RISPETTO AL PUBLISHER : QUI DEVO AVVIARE LA CONNECTION PER ABILITARE IL DELIVERY DEI CONNECTION
            queueConn.start();

            //Creo la Session
            QueueSession queueSession = queueConn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);

            //Creo il Receiver
            QueueReceiver receiver = queueSession.createReceiver(queue); //il receiver ascolta la queue

            //Creo il messaggio di ricezione
            TextMessage message;
            do{
                System.out.println("In attesa di messaggi!");
                //Ricezione BLOCCANTE
                message = (TextMessage) receiver.receive(); //USO receive per ricevere
                System.out.println("    "+" messaggio ricevuto: "+message.getText());
            }while(message.getText().compareTo("fine")!=0);

            //Cleanup
            receiver.close();
            queueSession.close();
            queueConn.close();

        } catch (Exception e) {
            System.out.println("Eccezoine : "+e.getMessage());
        }

    }

}
