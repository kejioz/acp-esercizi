package Client;

import Service.IDispatcher;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

public class Node {
    
    public static void main(String[] args) {
        
        System.out.println("[Node] Avviato...");
        System.out.println("[Node] Connessione con l'RMI Registry...");

        try{
            //Effettuo la lookup al rmiregistry
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IDispatcher dispatcher = (IDispatcher) rmiRegistry.lookup("dispatcher");
            System.out.println("[Node] Connessione riuscita!");
            //Avvio i thread workers che mi fanno la richiesta
            for (int i=0;i<5;i++){
                NodeThread worker = new NodeThread(dispatcher);
                System.out.println("[Node] Inizializzazione nuovo NodeThread..");
                worker.start();
            }
        }catch(NotBoundException e){
            System.err.println("[Node] Errore, il nome non è bindato");
        }catch(AccessException e){
            System.err.println("[Node] Errore, non mi è concesso di accedere");
        }catch(RemoteException e){
            System.err.println("[Node] Errore in getRegistry");
        }

    }

}
