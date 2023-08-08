package Server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;


public class LoggerImpl extends LoggerSkeleton {

    public synchronized void registraDato (int dato){

        try{

            //Mostro a schermo il dato
            System.out.println("[LoggerImpl] Il dato ricevuto è "+dato);

            //Registro su file
            FileWriter fw = new FileWriter("Saved.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(dato);
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
            System.out.println("[LoggerImpl] Il dato "+dato+" è stato salvato sul file Saved.txt con successo");


        }catch(Exception e){
            System.err.println("[LoggerImpl]");
        }
    }
    
}
