package Subscriber;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Services.IManager;

public class SubscriberServer {
    
    public static void main(String[] args) {
        
        System.out.println("[SubscriberServer] Avviato..");
        int idComponente = Integer.parseInt(args[0]);
        int porta = Integer.parseInt(args[1]);
        String nomefile = args[2];

        try{

            //Lookup rmiRegistry
            System.out.println("[SubscriberServer] Cerco il registro RMI...");
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IManager manager = (IManager) rmiRegistry.lookup("manager");
            System.out.println("[SubscriberServer] RMIRegistry localizzato con nome << manager >>");

            //Creazione del SubscriberImpl
            SubscriberImpl subscriber = new SubscriberImpl(idComponente,nomefile,manager,porta);
            System.out.println("[SubscriberServer] Subscriber creato!");

            //Creazione dello skeleton
            SubscriberSkeleton subscriberSkeleton = new SubscriberSkeleton(porta,subscriber);
            subscriberSkeleton.runSkeleton();
            System.out.println("[SubscriberServer] Skeleton avviato!");

        }catch(RemoteException e){
            System.err.println(e.getMessage());
        }catch(NotBoundException e){
            System.err.println(e.getMessage());
        }
    }

}
