package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class MagazzinoThread extends Thread{

    private MagazzinoSkeleton skeleton;
    private DatagramSocket socket;
    private DatagramPacket packet;

    public MagazzinoThread(MagazzinoSkeleton skeleton, DatagramSocket socket, DatagramPacket packet){

        this.socket=socket;
        this.skeleton=skeleton;
        this.packet=packet;

    }

    @Override
    public void run() {

        String message = new String(packet.getData(),0,packet.getLength());
        StringTokenizer tokens = new StringTokenizer(message,"#");
        System.out.println("[MagazzinoThread] Avviato, ricevuto messaggio "+message);

        String method = tokens.nextToken();
        String articolo = tokens.nextToken();
        
        if (method.compareTo("deposita")==0){

            try {

                int id = Integer.parseInt(tokens.nextToken());
                System.out.println("[MagazzinoThread] Ricevuta richiesta di deposito di "+articolo+" da id "+id);
                skeleton.deposita(articolo,id);
                System.out.println("[MagazzinoThread] Deposito effettuato di"+articolo+" da id "+id);

                //Creo risposta
                String ack = "ack";
                DatagramPacket response = new DatagramPacket(ack.getBytes(),ack.length(),packet.getAddress(),packet.getPort());
                socket.send(response);
                System.out.println((packet.getAddress())+""+packet.getPort());
                System.out.println("[MagazzinoThread] Mando un messaggio di conferma al richiedente di "+articolo+" da id "+id);

            } catch (UnknownHostException e) {
                System.err.println("[MagazzinoThread] Errore risposta");
            } catch (IOException e){
                System.err.println("[MagazzinoThread] Errore risposta");
            }
            
        }

        else if (method.compareTo("preleva")==0){
    
            try{

                System.out.println("[MagazzinoThread] Ricevuta richiesta di prelievo di "+articolo);
                int id = skeleton.preleva(articolo);
                System.out.println("[MagazzinoThread] Richiesta di preleva effettuta alla coda");
                String idresponse = Integer.toString(id);
                //Creo la risposta
                DatagramPacket response = new DatagramPacket(idresponse.getBytes(),idresponse.length(),packet.getAddress(),packet.getPort());
                socket.send(response);

                System.out.println("[Magazzino Thread] Prelevato l'articolo "+articolo+" dall'id "+id+", lo spedisco al richiedente");

                byte[] buf = new byte[1024];
                DatagramPacket ack = new DatagramPacket(buf, buf.length);
                socket.receive(ack);

                if (new String(ack.getData(),0,ack.getLength()).compareTo("ack")==0){
                    System.out.println("[MagazzinoThread] Ack ricevuto, l'articolo "+articolo+" dall'id "+id+" è stato ricevuto dal richiedente");
                }


            }catch (UnknownHostException e) {
                System.err.println("[MagazzinoThread] Errore risposta");
            } catch (IOException e){
                System.err.println("[MagazzinoThread] Errore risposta");
            }

        }

    }
    
}
