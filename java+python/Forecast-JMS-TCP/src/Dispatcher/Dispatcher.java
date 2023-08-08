package Dispatcher;

import java.util.Hashtable;
import javax.naming.*;
import javax.jms.*;

public class Dispatcher{

    public static void main(String[] args) {
        
        //Creo hash
        Hashtable<String,String> properties = new Hashtable<String,String>();

        //INserisco proprietà per provider e per activemqinitialcontet
        properties.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        properties.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        properties.put("queue.Request","Request");
        properties.put("queue.Response","Response");

        try {
            //passo la hashtable al mio initialcontext
            Context ctx = new InitialContext(properties);
            
            //Creo cfactory
            QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");

            //Creo code di richiesta e risposta
            Queue qrequest = (Queue) ctx.lookup("Request");
            Queue qresponse = (Queue) ctx.lookup("Response");

            //Creo connection e starto
            QueueConnection qc = qcf.createQueueConnection();
            qc.start();

            //Creo session
            QueueSession qs = qc.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);

            //Creo il receiver
            QueueReceiver receiver = qs.createReceiver(qrequest);

            //Passo al listener la sessione, la coda e il porto
            int port = Integer.valueOf(args[0]);

            DispatcherMsgListener listener = new DispatcherMsgListener(qs, qresponse, port);
            receiver.setMessageListener(listener);

        }catch(Exception e){

        }

    }


}