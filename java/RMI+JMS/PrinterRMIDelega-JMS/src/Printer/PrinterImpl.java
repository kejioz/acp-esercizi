package Printer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Service.IPrinter;

public class PrinterImpl implements IPrinter{
    
    String nomePrinter;

    public PrinterImpl(String nome){
        this.nomePrinter=nome;
    }

    @Override
    public synchronized void printDoc(String nomeDocumento) {
        try{

        Thread.sleep(5000);
        //Stampo a video
        System.out.println("[PrinterImpl] Ho ricevuto "+nomeDocumento);

            //Scrivo su file
            FileWriter fw = new FileWriter("documents.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(nomeDocumento);
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
            System.out.println("[PrinterImpl] Scrittura fatta con successo");

        }catch(IOException e){
            System.err.println("[PrinterImpl] Errore scrittura file");
        }catch(InterruptedException e){
            System.out.println("[PrinterImpl] Errore Interrupt");
        }
        
    }

}
