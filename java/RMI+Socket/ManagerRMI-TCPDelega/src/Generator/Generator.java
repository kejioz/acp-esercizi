package Generator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Services.IManager;

public class Generator {
 
    public static void main(String[] args) {
     
        System.out.println("[Generator] Avviato");

        try{

            //Lookup RMI
            System.out.println("[Generator] Cerco il registro RMI..");
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IManager manager = (IManager) rmiRegistry.lookup("manager");
            System.out.println("[Generator] Connessione con il manager effettuata");

            //Generazione Thread Workers
            System.out.println("[Generator] Genero i Thread");

            for (int i=0;i<3;i++){

                ThreadGenerator thread = new ThreadGenerator(manager);
                thread.run();
                System.out.println("[Generator] Avviato thread "+i);

            }

        }catch(RemoteException e){
            System.err.println(e.getMessage());
        }catch(NotBoundException e){
            System.err.println(e.getMessage());
        }

    }

}
