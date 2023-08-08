package Sensor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Coda.CodaCircolare;

public class TExecutor extends Thread{
    
    private CodaCircolare coda;

    public TExecutor (CodaCircolare coda){
        this.coda=coda;
        System.out.println("[TExecutor] Inizializzato!");
    }

    @Override
    public void run() {
        
        try{

            FileWriter fw = new FileWriter("CmdLog.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            while(true){
                String comando = coda.preleva();
                System.out.println("[TExecutor] Ho prelevato "+comando);
                pw.println(comando);
                pw.flush();
                System.out.println("[TExecutor] Stampato su CmdLog.txt "+comando);
                Thread.sleep(5000);
            }

        }catch(IOException e){
            System.err.println("[TExecutor] IOException, problema file");
        }catch(InterruptedException e){
            System.err.println("[TExecutor] InterruptedException");
        }

    }

}
