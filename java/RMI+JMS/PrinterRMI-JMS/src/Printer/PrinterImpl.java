package Printer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrinterImpl extends UnicastRemoteObject implements IPrinter {
    
    private String nomePrinter;

    public PrinterImpl(String nomePrinter) throws RemoteException{
        super();
        this.nomePrinter=nomePrinter;
    }

    @Override
    public synchronized void printDoc(String nomeDocumento) throws RemoteException {
       
        try {
            
            System.out.println("[PRINTER "+this.nomePrinter+"] RICEVUTA UPCALL DI STAMPA DI "+nomeDocumento+". LO SALVO SU FILE");
            
            FileWriter fw = new FileWriter("docs.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(nomeDocumento);
            pw.flush();

            pw.close();
            bw.close();
            fw.close();

            Thread.sleep(5000);

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

}
