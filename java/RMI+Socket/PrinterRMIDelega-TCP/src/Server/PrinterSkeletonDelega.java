package Server;

import Service.*;

import java.net.Socket;
import java.io.IOException;
import java.net.ServerSocket;

import java.rmi.registry.*;
import java.rmi.*;


//RICORDA CHE LO FACCIO PER DELEGA, QUINDI DEVO DELEGARE PRINTERIMPL DI IMPLEMENTARE I METODI. DEVO SOLO CALLARLI QUI. SMISTA ANCORA DI PIU' RISPETTO AD EREDITARIETA

public class PrinterSkeletonDelega implements IPrinter {

    private PrinterImpl printer;
    int porto;

    //Costruttore per istanziare Printer che poi chiama i metodi implementati da printerimpl
    protected PrinterSkeletonDelega (int p){
        printer = new PrinterImpl();
        porto = p;
    }
    
    //RunSkeleton per istanziare le connessioni
    public void runSkeleton(){

        try{
            System.out.println("[PrinterSkeleton] Looking up for RMIRegistry..");
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IDispatcher dispatcher = (IDispatcher) rmiRegistry.lookup("dispatcher");
            System.out.println("[PrinterSkeleton] Registry correctly bound! Named << dispatcher >>");
            ServerSocket serverSocket = null;
            dispatcher.addPrinter(this);
            try{
                serverSocket = new ServerSocket(porto);
                while (true){
                    System.out.println("[PrinterSkeleton] ServerSocket in ascolto sul porto "+porto);
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("[PrinterSkeleton] Socket accettata! ");
                    PrinterWorker thread = new PrinterWorker(clientSocket, this);
                    thread.start();
                    }
            }catch(IOException e){
                System.out.println("[PrinterSkeleton] Io exception in creaserver");
            }
        }catch(NotBoundException e){
            System.err.println("[PrinterSkeleton] Error NotBound");
        }catch(AccessException e){
            System.err.println("[PrinterSkeleton] Error AccessException");
        }catch(RemoteException e){
            System.err.println("[PrinterSkeleton] Error Remote");
            System.err.println(e.getMessage());
        }

    }

    @Override
    public boolean print(String docName) {
        boolean result = printer.print(docName);
        return result;
    }

    public int getPorto(){
        return porto;
    }



}
