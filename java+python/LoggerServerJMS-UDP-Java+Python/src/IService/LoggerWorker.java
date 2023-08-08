package IService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
public class LoggerWorker extends Thread{
    
    private LoggerSkeleton skeleton;
    private Socket client;
    private BufferedInputStream input;
    

    protected LoggerWorker(LoggerSkeleton sk, Socket cl){

        skeleton = sk;
        client = cl;

        try{

            input = new BufferedInputStream(client.getInputStream());
            System.out.println("[WORKER] Correttamente avviato..");

        }catch(IOException e){

            e.printStackTrace();

        }

    }

    @Override
    public void run() {

        try{
            byte[] datobyte = new byte[10];
            input.read(datobyte,0,datobyte.length);
            System.out.println(datobyte.length);
            String datostring = new String(datobyte,0,datobyte.length);
            System.out.println("[WORKER] DATOBYTE : "+datostring);
            System.out.println(datostring);
            Integer dato = Integer.parseInt(datostring);
            System.out.println("Il dato è "+dato);
            System.out.println("[WORKER] Ricevuto dato "+dato+", effettuo upcall.");
            skeleton.registraDato(dato);

        }catch(IOException e){
            e.printStackTrace();
        }
        

    }

}
