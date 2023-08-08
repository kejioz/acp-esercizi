package Generator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Service.IDispatcher;

public class Generator {
    
    public static void main(String[] args) {

        //Bindo il registry ed avvio i thread

        try {

            //Cerco il registro
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IDispatcher dispatcher = (IDispatcher) rmiRegistry.lookup("dispatcher");
            System.out.println("[Generator] Lookup effettuata al registro, trovato << dispatcher >> ");

            //Avvio i thread a cui passo il dispatcher
            for (int i=0;i<3;i++){
                System.out.println("[Generator] Avviato thread "+i+1);
                GeneratorThread thread = new GeneratorThread(dispatcher);
                thread.run();
            }
            


        } catch (RemoteException e) {
            System.err.println("[Generator] "+e.getMessage());
        } catch (NotBoundException e){
            System.err.println("[Generator] "+e.getMessage());
        }

    }

}
