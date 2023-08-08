package magazzino;

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import coda.*;
import codaimpl.*;


public class Magazzino {
    
    public static void main(String[] args){

        //Setto dove contattare il provider e dove prendere il contesto inziale
        Hashtable <String,String> p = new Hashtable<String,String>();
        p.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        p.put("java.naming.provider.url","tcp://127.0.0.1:61616");

        //Definisco il nome della coda
        p.put("queue.Richiesta","Richiesta");

        //Creo il contesto iniziale a valle delle proprietà che ho impostato nella hashtable p
        try {
            Context ctx = new InitialContext(p);
            //Lookup degli Administered Objects cioè ConnectionFactory e Destination
            QueueConnectionFactory qconnf = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory"); //ricorda di fare typecasting
            Queue queueRequest = (Queue) ctx.lookup("Richiesta");

            //Creo la connessione
            QueueConnection qconn = qconnf.createQueueConnection();
            qconn.start(); //Lo devo fare siccome sono nel Consumer, se fossi nel Producer non dovrei farlo

            //Creo la Sessione
            QueueSession qsession = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creo il Receiver
            QueueReceiver qreceiver = qsession.createReceiver(queueRequest);

            //Creo un MessageListener che mi va a creare un thread quando ricevo un messaggio
                //Ovviamente devo prima creare la coda da passargli
            Coda coda = new CodaSynch(new CodaCircolare(5));
            MagazzinoListener l = new MagazzinoListener(coda,qconn);

            //Ricezione Asincrona
            qreceiver.setMessageListener(l);

            //QUINDI PER ORA HO GESTITO SOLTANTO LA CODA DI RECEIVER, L'ALTRA CODA LA GESTISCO CON I CLIENT

        } catch (NamingException e) {
            System.err.println("[Magazzino] Naming Exception");
        } catch (JMSException e){
            System.err.println("[Magazzino] JMS Exception");
        }


    }

}
