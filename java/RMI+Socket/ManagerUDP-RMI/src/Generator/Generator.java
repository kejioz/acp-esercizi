package Generator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import AlertNotification.AlertNotification;
import Manager.IManager;

public class Generator{

    public static void main(String[] args) {

        try{

            Registry registry = LocateRegistry.getRegistry();
            IManager manager = (IManager) registry.lookup("manager");

            System.out.println("[Generator] Connessione con il registro effettuata");
            
            System.out.println("[Generator] Avvio dei Thread..");

            GeneratorThread[] generators = new GeneratorThread[3];

            //Passo il manager per l'upcall ai threads
            for (int i=0;i<3;i++){
                generators[i] = new GeneratorThread(manager);
                System.out.println("[Generator] Thread "+i+" startato");
                generators[i].start();
            }

        }catch(RemoteException e){
            e.printStackTrace();
        }catch(NotBoundException e){
            e.printStackTrace();
        }

    }


}