package Printer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Service.IPrinter;

public class ServerPrinter {
    
    public static void main(String[] args) {
        
        try{

            //Prendo il nome simbolico della printer
            String nomePrinter = args[0];

            //Creazione RMIRegistry
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IPrinter printer = new PrinterImpl(nomePrinter);
            IPrinter printerStub = (IPrinter) UnicastRemoteObject.exportObject(printer,0);
            rmiRegistry.rebind(nomePrinter,printerStub);
            System.out.println("[ServerPrinter] Servizio RMI correttamente bindato");

        }catch(RemoteException e){
            System.err.println("[ServerPrinter] Remote Exception "+e.getMessage());
        }

    }

}
