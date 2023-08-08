package client;

import javax.jms.*;

public class ClientListener implements MessageListener{

    @Override
    public void onMessage(Message message) {

        MapMessage msg = (MapMessage) message;
        
        try {
            System.out.println("[ClientListener] Messaggio ricevuto: "+msg.getString("richiesta")+" di "+msg.getInt("id_articolo"));
        } catch (JMSException e) {
            System.err.println("[ClientListener] Errore nella lettura del messaggio");
        }

    }
    
}
