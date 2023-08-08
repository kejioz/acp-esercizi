package Client;

import javax.jms.*;


public class ReceiverListener implements MessageListener{
    
    private MapMessage msg;

    public ReceiverListener(){
        super();
    }

    @Override
    public void onMessage(Message message) {
        
        try{
            msg = (MapMessage) message;
            System.out.println("[Listener] Ho ricevuto correttamente "+msg.getString("idArticolo"));
        }catch(JMSException e){
            System.err.println("[Listener] JMSException");
        }

    }
}
