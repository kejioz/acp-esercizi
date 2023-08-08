package Proxy;

import javax.naming.*;

import Server.IServer;

import javax.jms.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;

public class Proxy {
    
    public static void main(String[] args) {
        
        //Gestisco JMS
        Hashtable <String,String> prop = new Hashtable <String,String>();

        //Inserisco nella hashtable le proprietà di activemq
        prop.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        prop.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        prop.put("queue.Richiesta","Richiesta");
        prop.put("queue.Risposta","Risposta");

        try {
            //Caricato il contesto dalla mia hasthable
            Context ctx = new InitialContext(prop);
            
            //Prendo administered objects
            QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");
            Queue richiesta = (Queue) ctx.lookup("Richiesta");
            Queue risposta = (Queue) ctx.lookup("Risposta");

            //Creo connection 
            QueueConnection qconn = qcf.createQueueConnection();

            //Mi prendo il registro RMI
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IServer server = (IServer) rmiRegistry.lookup("server");

            //Creo il receiver a cui passo la connection, la richiesta ed anche il riferimento al servizio RMI
            ProxyReceiver receiver = new ProxyReceiver(qconn, richiesta,risposta, server);
            receiver.start();
            System.out.println("[Proxy] Proxy startato, RMIRegistry trovato e thread receiver avviato.");

        } catch (JMSException e) {
            System.err.println("JMSException in Proxy");
        } catch (NamingException e){
            System.err.println("Naming Exception in Proxy");
        } catch (RemoteException e){
            System.err.println("RemoteException in Proxy");
        } catch (NotBoundException e){
            System.err.println("Notbound exception in Proxy");
        }

    }

}
