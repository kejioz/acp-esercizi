package client;

import javax.jms.*;

public class ClientListener implements MessageListener {
    
    @Override
    public void onMessage(Message message) {

        MapMessage msg = (MapMessage) message;

        System.out.println("[ClientListener] Messaggio ricevuto: "+msg);
    }
}
