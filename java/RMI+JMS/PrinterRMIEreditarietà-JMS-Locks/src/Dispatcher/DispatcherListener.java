package Dispatcher;

import javax.jms.Message;
import javax.jms.MessageListener;

public class DispatcherListener implements MessageListener{
    
    public DispatcherListener(){
        super();
    }

    @Override
    public void onMessage(Message mess) {
        
        System.out.println("[DispatcherListener] Messaggio ricevuto, lo passo al thread");
        DispatcherThread runnable = new DispatcherThread(mess);
        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("[DispatcherListener] Thread startato");



    }

}
