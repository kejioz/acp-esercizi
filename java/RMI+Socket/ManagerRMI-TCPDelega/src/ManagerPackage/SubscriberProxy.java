package ManagerPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import Services.ISubscriber;

public class SubscriberProxy implements ISubscriber{
    
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private int idComponente;
    private int porta;

    public SubscriberProxy (int porta,int idComponente){

            this.porta=porta;
            this.idComponente = idComponente;
            System.out.println("[Proxy] Porta "+porta+" idComponente "+idComponente);
            
            //Nota : NON POSSO FARE LA SOCKET NEL COSTRUTTORE PERCHE' IN SUBSCRIBER SERVER FACCIO PRIMA SUBSCRIBE E POI RUNSKELETON
            //QUINDI SE FACESSI QUI LA SOCKET LO SKELETON NON SAREBBE ANCORA AVVIATO

    }

    @Override
    public void notifyAlert(int criticality) {
        
        try{

            //Creo la socket
            this.socket = new Socket(InetAddress.getLocalHost(), porta);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            System.out.println("[SubscriberProxy] Correttamente istanziato!");
            
            //Spedisco allo skeleton
            output.writeInt(criticality);
            System.out.println("[SubscriberProxy] Il manager ha richiesto di mandare un alert di criticità "+criticality+" l'ho spedito con successo allo skeleton");

            //Prendo ack
            String ack =input.readUTF();
            if (ack.compareTo("ack")==0){
                System.out.println("[SubscriberProxy] Ack ricevuto!");
            }
            else{
                System.out.println("[SubscriberProxy] Ack non ricevuto! C'è stato un errore");
            }
            
        }catch(IOException e){
            System.err.println("[SubscriberProxy] IOException nel notifyalert");
        }

    }

    public int getIdComponente(){
        return this.idComponente;
    }


}
