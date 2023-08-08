package proxy;

import magazzino.*;

import java.rmi.RemoteException;

import javax.jms.*;

public class ProxyListener implements MessageListener{

    private Queue queueResponse;
    private QueueConnection qconn;
    private Magazzino magazzino;

    public ProxyListener (Queue queueResponse,QueueConnection qconn,Magazzino magazzino){
        this.queueResponse=queueResponse;
        this.qconn=qconn;
        this.magazzino=magazzino;
        System.out.println("[ProxyListener] Successfully created!");
    }
    
    @Override
    public void onMessage(Message message) {
        
        //Connessione RMI e parsing mess
        try {
            //Parsing del messaggio
            MapMessage mapmessage = (MapMessage) message;
            String metodo = mapmessage.getString("richiesta");
            int id_articolo = mapmessage.getInt("id_articolo");
            System.out.println("[ProxyListener] Letto un messaggio di "+metodo+" di articolo "+id_articolo);

            //In base alla richiesta evoco il metodo sul magazzino
            if (metodo.compareTo("preleva")==0){

                //Creo un Thread Sender a cui passo la coda su cui fare richiesta e l'id articolo
                 ThreadSender sender = new ThreadSender(queueResponse, magazzino, id_articolo, qconn);
                 System.out.println("[ProxyListener] Sender creato");
                 sender.start();

            }
            else{

                magazzino.deposita(id_articolo);
                System.out.println("[ProxyListener] Chiamato metodo di deposito RMI con valore "+id_articolo);

            }
            
         
        } catch (JMSException e){
            System.err.println("[ProxyListener] JMSException");
        } catch (RemoteException e){
            System.err.println("[ProxyListener] Remote Exception");
        }


        
    }

}
