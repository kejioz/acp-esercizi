package magazzino;

import coda.*;

import javax.jms.*;

public class MagazzinoThread extends Thread {
    
    private Coda coda;
    private MapMessage mm;
    private QueueConnection qconn;


    public MagazzinoThread(Coda c, MapMessage m, QueueConnection qc){
        this.coda=c;
        this.mm=m;
        this.qconn=qc;
    }

    public void run (){

        try{
            // |operazione|dep/prel|
            // |id_articol|   id   |
            //Mi prendo l'operazione dalla Mappa
            String op = mm.getString("operazione");
            //Mi prendo il valore
            int val = mm.getInt("valore");

            //Che operazione ho? If quindi
            if (op.compareTo("deposita")==0){
                System.out.println("[MagazzinoThreaad] Operazione "+op+" valore "+val);
                coda.inserisci(val);
            }
            else{

                val = coda.preleva();

                //Creo la Sessione, ricorda che ogni thread crea una sessione a partire dalla Connection che gli passo
                QueueSession qsession = qconn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                //Creo il Sender sfruttando l'header, siccome mi devo sincronizzare sulla stessa coda
                QueueSender qsender = qsession.createSender((Queue) mm.getJMSReplyTo()); //Ricorda di fare il casting in Queue

                //Creo il messaggio di risposta
                MapMessage reply = qsession.createMapMessage();
                reply.setString("operazione","risultato");
                reply.setInt("valore",val);

                //Mando la risposta
                qsender.send(reply);

                //Cleanup delle risorse
                qsender.close();
                qsession.close();
            }
        }catch(JMSException e){
            System.err.println("[MagazzinoThread] JMSException");
        }
    }

}
