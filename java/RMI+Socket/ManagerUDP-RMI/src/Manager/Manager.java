package Manager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Manager {
    
    public static void main(String[] args) {
        
        try{
            //Avvio il registro
            Registry registry = LocateRegistry.getRegistry();
            IManager manager = new ManagerImpl();
            registry.rebind("manager",manager);
            System.out.println("[Manager] Servizio bindato con nome << manager >>");
        }catch(RemoteException e){
            e.printStackTrace();
        }

    }

}
