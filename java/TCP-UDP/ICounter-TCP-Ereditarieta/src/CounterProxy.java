import java.net.Socket;
import java.net.SocketException;
import java.io.*;
import java.net.*;

//L'obiettivo di questo proxy è prendere le richieste dal client. Il client inizializzerà un oggetto di tipo CounterProxy e ne invocherà i metodi passando i parametri
//Il counter proxy manderà le richieste allo Skeleton che le handlerà. Utilizzo un Socket ed i flussi in e out che sono socket.getinputstream e socket.getoutputstream
//Per i messaggi che manderò allo skeleton utilizzo delle stringhe che poi parso lato server. Una volta mandati i messaggi mi metto in ascolto per ricevere le risposte
//Le risposte poi le ritorno

public class CounterProxy implements ICounter{

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public CounterProxy (){
        try {
            socket = new Socket(InetAddress.getLocalHost(),5000);   //creato un socket che ha come ip il localhost siccome il server sta qua e la porta 5000
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    //creato il socket lato client. Ora devo implementare i metodi.

    public void setCount(String id,int s){
        System.out.println("Il client "+id+" setta il counter a "+s);
        try {
            String messaggio = new String ("set#"+s+"#");
            out.writeUTF(messaggio);
            //ho creato il messaggio. ora devo mandarlo in output con flush
            out.flush();
            System.out.println("[Proxy] Messaggio inviato, attendo l'acknowledgment.");

            //messaggio di ack
            String ack = new String (in.readUTF());
            if (ack.equals("1")){
                System.out.println("[Proxy] Acknowledged");
            }
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public int sum (int s){
        int x=0;
        System.out.println("Si somma "+s+" al conteggio");

        try {
            //sendo il mess
            String messaggio = new String("sum#"+s+"#");
            out.writeUTF(messaggio);
            out.flush();
            System.out.println("[Proxy] Valore inviato, attendo la risposta...");
            //Attendo rsposta
            x= in.readInt();
            return x;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return 0;
    }

    public int increment(){
        int x =0;
        System.out.println("Si somma 1 al conteggio");
        try {
            String message = ("increment#");
            out.writeUTF(message);
            out.flush();
            //mandato il messaggio , ora attendo la risposta
            System.out.println("Messaggio inviato, in attesa di risposta...");
            x = in.readInt();
            return x;
            
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return 0;
    }

}
