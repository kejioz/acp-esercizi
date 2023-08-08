package Printer;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Service.IPrinter;

public class Printer {
    
    public static void main(String[] args) {
        
        try{

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IPrinter printer = new PrinterImpl(args[0]);
            rmiRegistry.rebind(args[0], printer);
            System.out.println("[PrinterServer] Servizio bindato correttamente");

        }catch(RemoteException e){
            System.err.println("[PrinterServer] RemoteException");
        }

    }

}
