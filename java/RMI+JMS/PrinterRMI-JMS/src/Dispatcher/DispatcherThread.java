package Dispatcher;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.jms.JMSException;
import javax.jms.MapMessage;

import Printer.IPrinter;

public class DispatcherThread extends Thread {
    
    private MapMessage message;

    protected DispatcherThread(MapMessage message){
        this.message = message;
    }
    
    @Override
    public void run() {

        try{

            String nomeDocumento = message.getString("nomeDocumento");
            String nomePrinter = message.getString("nomePrinter");
            System.out.println("[THREAD] AVVIATO, IL NOME DEL DOCUMENTO E' "+nomeDocumento+". CERCO LA PRINTER "+nomePrinter);

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IPrinter printer = (IPrinter) rmiRegistry.lookup(nomePrinter);
            System.out.println("[THREAD] PRINTER "+nomePrinter+" BINDATA, EFFETTUO UPCALL");

            printer.printDoc(nomeDocumento);
            System.out.println("[THREAD] UPCALL A "+nomePrinter+" EFFETTUATA CON SUCCESSO");

        }catch(JMSException | RemoteException | NotBoundException e){

            System.out.println(e.getMessage());

        }

    }


}
