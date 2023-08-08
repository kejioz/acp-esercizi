package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import Interface.IMagazzino;

public class MagazzinoProxy implements IMagazzino{

    private DatagramSocket socket;

    public MagazzinoProxy(){
        try{
            socket = new DatagramSocket();
            System.err.println("[Proxy] Avviato sul porto "+socket.getLocalPort());
        }
        catch(SocketException e){
            System.err.println("[Proxy] Errore socket");
        }
    }

    @Override
    public void deposita(String articolo, int id) {
        
        String stringaRichiesta = ("deposita#"+articolo+"#"+id+"#");
        try{
            DatagramPacket richiesta = new DatagramPacket(stringaRichiesta.getBytes(),stringaRichiesta.getBytes().length,InetAddress.getLocalHost(),3000);
            socket.send(richiesta);
            System.out.println("[Proxy] Inoltrata allo skeleton una richiesta di deposito di "+articolo+" dall'id "+id);

            //Aspetto l'ack
            byte[] buf = new byte[1024];
            DatagramPacket risposta = new DatagramPacket(buf,buf.length);
            socket.receive(risposta);
            String response =new String(risposta.getData(),0,risposta.getLength());
            System.out.println(response);

            if (response.compareTo("ack")==0){
                System.out.println("[Proxy] Deposito dell'articolo"+articolo+" con id "+id+" avvenuto con successo");
            }

        }catch(UnknownHostException e){
            System.err.println("[Proxy] Unknown Host");
        }catch (IOException e){
            System.err.println("[Proxy] IoException");
        }

        
    }

    @Override
    public int preleva(String articolo) {
        
        String stringaRichiesta = ("preleva#"+articolo+"#");
        int id=-1;

        try{
            DatagramPacket richiesta = new DatagramPacket(stringaRichiesta.getBytes(),stringaRichiesta.length(),InetAddress.getLocalHost(),3000);
            socket.send(richiesta);
            System.out.println("[Proxy] Inoltrata allo skeleton una richiesta di prelievo di "+articolo);
            

            //Aspetto la risposta
            byte[] buf = new byte[1024];
            DatagramPacket risposta = new DatagramPacket(buf,buf.length);
            socket.receive(risposta);

            String stringaId = new String (risposta.getData(),0,risposta.getLength());
            id = Integer.parseInt(stringaId);

        }catch(UnknownHostException e){
            System.err.println("[Proxy] Unknown Host");
        }catch (IOException e){
            System.err.println("[Proxy] IoException");
        }

        return id;

    }
    
}
