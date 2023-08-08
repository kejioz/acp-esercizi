package Server;

import Service.IPrinter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class PrinterImpl implements IPrinter {
    
    //member area
    private static final long serialVersionUID=10L;

    private Semaphore semaforo;

    //costruttore
    public PrinterImpl (){
        semaforo = new Semaphore(1);
    }

    @Override
    public boolean print(String docName) {
        
        //Se il tryacquire mi restituisce false ritorno false, non posso esaudire la richiesta
        if(!semaforo.tryAcquire()){
            System.out.println("[Printer] Occupato! Non posso esaudire la richiesta");
            return false;
        }

        //Altrimenti skippo il blocco di sopra, e posso acquisire il lock
        try{
            System.out.println("[Printer] Posso esaudire la richiesta...stampo");
            //tempo random tra 5 e 10 secondi
            Random rand = new Random();
            Thread.sleep((rand.nextInt(6)+5)*1000);
            //stampo a video docname
            System.out.println("\n"+docName+"\n");
            //salvo su file docs.txt docname
            System.out.println("[Printer] Adesso salvo su file...");
            FileWriter writer = new FileWriter("docs.txt",true);
            BufferedWriter bw = new BufferedWriter(writer);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(docName);
            pw.flush();
            pw.close();
            bw.close();
            writer.close();
        }catch(InterruptedException e){
            System.err.println("[Printer] Errore interrupted");
        }catch(IOException e){
            System.err.println("[Printer] Errore IO");
        }finally{
            semaforo.release();
        }
        
        return true;
    }



}
