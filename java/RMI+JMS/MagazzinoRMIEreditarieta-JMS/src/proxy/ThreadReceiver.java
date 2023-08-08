package proxy;

import magazzino.*;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.jms.*;

public class ThreadReceiver extends Thread{
    
    QueueConnection qconn;
    Queue queueRequest;
    Queue queueResponse;

    public ThreadReceiver(QueueConnection qconn,Queue queueRequest,Queue queueResponse){
        super();
        this.qconn=qconn;
        this.queueRequest=queueRequest;
        this.queueResponse=queueResponse;
    }

    @Override
    public void run() {

        try {
            qconn.start();
            System.out.println("[ThreadReceiver] Started Connection");
            //Creo la session
            QueueSession queueSession = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            System.out.println("[ThreadReceiver] Created Session");

            //Creo il receiver
            QueueReceiver queueReceiver = queueSession.createReceiver(queueRequest);
            System.out.println("[ThreadReceiver] Created Receiver");

            //Creo il registry da passare al listener
            Registry rmiRegistry = LocateRegistry.getRegistry();
            Magazzino magazzino = (Magazzino) rmiRegistry.lookup("magazzino");
            System.out.println("[ProxyListener] Connessione riuscita con RMI");

            //Creo il listener a cui passo la coda, la connection ed il magazzino
            ProxyListener listener = new ProxyListener(queueResponse,qconn,magazzino);
            System.out.println("[ThreadReceiver] Created Listener");

            //Setto il listener al receiver
            queueReceiver.setMessageListener(listener);
            System.out.println("[ThreadReceiver] Set Listener to Receiver");


        } catch (JMSException e) {
            System.err.println("[ThreadReceiver] JMSException" +e.getMessage());
        } catch (RemoteException e) {
            System.err.println("[ThreadReceiver] RemoteException");
        } catch (NotBoundException e){
            System.err.println("[ThreadReceiver] NotBound exception");
        }

    }

}
