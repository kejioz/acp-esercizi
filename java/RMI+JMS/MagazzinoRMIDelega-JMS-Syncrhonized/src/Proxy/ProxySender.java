package Proxy;


import java.rmi.RemoteException;

import javax.jms.*;

import Server.IServer;

public class ProxySender extends Thread{
    
    private Queue risposta;
    private IServer server;
    private QueueConnection qconn;

    protected ProxySender(IServer server,Queue risposta,QueueConnection qconn){
        this.risposta = risposta;
        this.server=server;
        this.qconn=qconn;
    }

    @Override
    public void run() {
        
        try{

            //Effettuo richiesta di preleva
            int idArticolo = server.preleva();
            System.out.println("[Sender] Prelevato dal server "+idArticolo);
            //Creo session
            QueueSession qsess = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creo messaggio
            MapMessage msg = qsess.createMapMessage();
            msg.setString("tipoRichiesta", "preleva");
            msg.setInt("idArticolo",idArticolo);

            //Creo sender
            QueueSender sender = qsess.createSender(risposta);
            System.out.println("[Sender] Sender creato su coda risposta");
            
            //Spedisco mess
            sender.send(msg);
            System.out.println("[Sender] Mandato ack di prelievo di "+idArticolo+" sulla coda Risposta");


        }catch(RemoteException e){
            System.err.println("RemoteException in Sender "+e.getMessage());
        }catch(JMSException e){
            System.out.println("JMSException in Sender");
        }


    }


}
