package Disk;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TopicConnection;

public class DiskListener implements MessageListener {

    private TopicConnection tconn;

    public DiskListener (TopicConnection tconn){
        this.tconn=tconn;
    }

    @Override
    public void onMessage(Message message) {
        
        try{

            System.out.println("[DiskListener] Messaggio ricevuto");
            MapMessage mapmessage = (MapMessage) message;
            DiskThread thread = new DiskThread(mapmessage);
            thread.run();


        }catch(Exception e){
            System.err.println("[DiskListener] Errore JMS");
        }

    }
    
}
