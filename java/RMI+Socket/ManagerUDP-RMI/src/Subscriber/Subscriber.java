package Subscriber;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import AlertNotification.AlertNotification;
import Manager.IManager;

public class Subscriber {
    
    public static void main(String[] args) {
        
        try{

            //Conessione RMI
            Registry registry = LocateRegistry.getRegistry();
            IManager manager = (IManager) registry.lookup("manager");

            System.out.println("[Subscriber] Connessione con il registry effettuata");

            //Prendo id componente, porta e nomefile
            int idcomponente = Integer.parseInt(args[0]);
            int porta = Integer.parseInt(args[1]);
            String nomefile = args[2];

            //Effettuo la subscribe
            manager.subscribe(idcomponente, porta);
            System.out.println("[Subscriber] Subscribe effettuata su "+idcomponente+" e porta "+porta);

            //Runno lo skeleton
            SubscriberSkeleton skeleton = new SubscriberSkeleton(porta,nomefile);
            System.out.println("[Subscriber] Avvio lo skeleton...");
            skeleton.runSkeleton();

        }catch(RemoteException e){
            e.printStackTrace();
        }catch(NotBoundException e){
            e.printStackTrace();
        }


    }

}
