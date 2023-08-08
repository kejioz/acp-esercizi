package Server;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class PrinterWorker extends Thread {

    private Socket clientSocket;
    private PrinterSkeletonDelega skeleton;
    private DataInputStream in;
    private DataOutputStream out;

    protected PrinterWorker(Socket client, PrinterSkeletonDelega sk){
        clientSocket=client;
        skeleton=sk;
        try{
            in= new DataInputStream(clientSocket.getInputStream());
            out= new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("[Worker] Inizializzato correttamente!");
        }catch(IOException e){
            System.err.println("[PrinterWorker] Errore nei DataStreams");
        }
    }
    
    //Cosa deve fare il Worker? Deve chiamarmi i metodi dello skeleton, e per capire quale devo capirlo dai DataStreams del client
    @Override
    public void run() {
        
        try {
            System.out.println("[Worker] Lettura messaggio...");
            //Leggo il messaggio in input
            String message = in.readUTF();
            System.out.println("[Worker] Messaggio letto!");
            //Utilizzo uno StringTokenizer per stampare
            StringTokenizer tokens = new StringTokenizer(message,"#");
            //Adesso leggo il metodo
            String method = tokens.nextToken();

            //Se il metodo letto è print allora procedo a stampare
            if (method.compareTo("print")==0){
                //Devo leggere la Stringa docName
                String docName = tokens.nextToken();
                //Adesso posso chiamare il metodo di print che viene esposto dallo skeleton
                System.out.println("[Worker] Mando la print di "+docName);
                boolean ok =skeleton.print(docName);
                //Mando ack
                System.out.println("[Worker] Mando il risultato dell'operazione... ("+ok+")");
                out.writeBoolean(ok);
            }

        } catch (IOException e) {
            System.err.println("[PrinterWorker] IOException");
        }


    }
    
}
