package magazzino;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MagazzinoServer {
    
    public static void main(String[] args) {
        
        try{
            
            Magazzino magazzino = new MagazzinoImpl();
            Registry rmiRegistry = LocateRegistry.getRegistry();
            rmiRegistry.rebind("magazzino",magazzino);
            System.out.println("[MagazzinoServer] Servizio bindato con il nome << magazzino >>");
            System.out.println("[MagazzinoServer] In ascolto...");

        }catch(RemoteException e){
            System.err.println("[MagazzinoServer] Errore "+e.getMessage());
        }


    }
}
