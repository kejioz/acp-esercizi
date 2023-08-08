package Dispatcher;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class DispatcherListener implements MessageListener{

    @Override
    public void onMessage(Message message) {
        
        System.out.println("[DispatcherListener] Messaggio ricevuto");
        MapMessage mapmessage = (MapMessage) message;
        DispatcherThread thread = new DispatcherThread(mapmessage);
        thread.start();

    }
    
}
