package Printer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Printer {
    

    public static void main(String[] args) {
        
        try {

            String nomePrinter = args[0];
            Registry rmiRegistry = LocateRegistry.getRegistry();
            PrinterImpl printer = new PrinterImpl(nomePrinter);
            rmiRegistry.rebind(nomePrinter, printer);
            System.out.println("[PRINTER "+nomePrinter+"] BINDATA SU RMIREGISTRY CON NOME << "+nomePrinter+" >>");


        } catch (RemoteException e) {

            System.err.println(e.getMessage());

        }
            
    }


}
