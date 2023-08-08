package Printer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.ReentrantLock;

import Service.IPrinter;

public class PrinterImpl extends UnicastRemoteObject implements IPrinter{

    private String nomefile;
    private final ReentrantLock lockprinter;

    public PrinterImpl(String nomefile) throws RemoteException{
        super();
        this.nomefile=nomefile;
        lockprinter=new ReentrantLock();
    }

    @Override
    public void printDoc(String documento) throws RemoteException {
        
        lockprinter.lock();

        try {
            
            //Stampo il nome del documento
            System.out.println("[Printer] Il nome del documento è "+documento);

            //Salvo su file
            FileWriter fw = new FileWriter(nomefile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println("doc"+documento);
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
            System.out.println("[Printer] Scritto su file "+documento);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            lockprinter.unlock();
        }

    }

}
