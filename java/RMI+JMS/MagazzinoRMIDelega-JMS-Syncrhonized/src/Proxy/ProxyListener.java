package Proxy;

import java.rmi.RemoteException;

import javax.jms.*;

import Server.IServer;

public class ProxyListener implements MessageListener{
    
    private MapMessage msg;
    private IServer server;
    private Queue richiesta;
    private Queue risposta;
    private QueueConnection qconn;

    public ProxyListener(IServer server,Queue richiesta,Queue risposta,QueueConnection qconn){
        super();
        this.server=server;
        this.richiesta=richiesta;
        this.risposta=risposta;
        this.qconn=qconn;
    }

    @Override
    public void onMessage(Message message) {
        
        try{
            msg = (MapMessage) message;
            String tipoRichiesta = msg.getString("tipoRichiesta");
            int idArticolo = (msg.getInt("idArticolo"));
            System.out.println("[Listener] Ho ricevuto correttamente una richiesta di "+tipoRichiesta+ (tipoRichiesta.compareTo("deposita")==0 ? " di "+idArticolo : ' '));

            if (tipoRichiesta.compareTo("deposita")==0){
                server.deposita(Integer.parseInt(msg.getString("idArticolo")));
                System.out.println("[Listener] Ho mandato una richiesta di deposito di "+msg.getString("idArticolo"));
            }
            else if (tipoRichiesta.compareTo("preleva")==0){
                
                ProxySender sender = new ProxySender(server,risposta,qconn);
                sender.start();

            }
            else{
                System.out.println("[Listener] errore nelle richieste JMS");
            }

            System.out.println("[Listener] Richiesta RMI correttamente inoltrata");

        }catch(JMSException e){
            System.err.println("[Listener] JMSException");
        }catch (RemoteException e){
            System.err.println("[Listener] RemoteException");
        }

    }
}

