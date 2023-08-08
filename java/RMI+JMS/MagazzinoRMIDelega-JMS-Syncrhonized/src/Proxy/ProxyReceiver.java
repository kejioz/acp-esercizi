package Proxy;

import javax.jms.*;

import Server.IServer;

public class ProxyReceiver extends Thread{

    private Queue richiesta;
    private Queue risposta;
    private QueueConnection qconn;
    private IServer server;
    
    protected ProxyReceiver(QueueConnection qconn,Queue richiesta,Queue risposta, IServer server){
        this.richiesta=richiesta;
        this.risposta=risposta;
        this.qconn=qconn;
        this.server=server;
    }

    @Override
    public void run() {
        
        try{
            //Sono il receiver quindi devo startare la connection
            qconn.start();

            //Starto la session
            QueueSession qsess = qconn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);

            //Creo il receiver
            QueueReceiver qreceiver = qsess.createReceiver(richiesta);

            //Setto il listener, mi passo anche il registro RMI per fare le call, e mi passo anche le due code
            ProxyListener listener = new ProxyListener(server,richiesta,risposta,qconn);
            qreceiver.setMessageListener(listener);

        } catch (JMSException e) {
            System.err.println("JMSException in Thread");
        }

    }
    
}

