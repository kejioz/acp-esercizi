package Manager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Service.IManager;

public class Manager {
    
    public static void main(String[] args) {
        
        try {
            Registry registry = LocateRegistry.getRegistry();
            IManager manager = new ManagerImpl();
            registry.rebind("manager", manager);
            System.out.println("[MANAGER] BINDATO CON SUCCESSO SERVIZIO CON NOME <<manager>>");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
