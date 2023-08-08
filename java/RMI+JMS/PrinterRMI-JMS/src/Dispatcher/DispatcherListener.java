package Dispatcher;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class DispatcherListener implements MessageListener {
    
    protected DispatcherListener(){
        super();
    }

    @Override
    public void onMessage(Message message) {
       
        MapMessage mess = (MapMessage) message;
        System.out.println("[DISPATCHER - JMS] MESSAGGIO RICEVUTO, AVVIO UN THREAD PER ESTRARRE IL MESSAGGIO");
        DispatcherThread thread = new DispatcherThread(mess);
        thread.start();
        
    }

}
