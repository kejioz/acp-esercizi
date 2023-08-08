package Generator;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Service.IManager;

public class Generator {
    
    public static void main(String[] args) {
        
        
        try {

            //Cerco registry
            Registry registry = LocateRegistry.getRegistry();
            IManager manager = (IManager) registry.lookup("manager");
            System.out.println("[GENERATOR] TROVATO MANAGER NEL REGISTRY");
            
            //Avvio i thread a cui passo il manager
            System.out.println("[GENERATOR] AVVIATO, AVVIO TRE THREAD");
            GeneratorThread[] generators = new GeneratorThread[5];

            for (int i=0;i<5;i++){
                generators[i] = new GeneratorThread(manager);
                System.out.println("[GENERATOR] THREAD "+i+" AVVIATO");
                generators[i].run();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
