package Dispatcher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Service.IDispatcher;



public class DispatcherServer {

    public static void main(String[] args) {
        
        try{
            //Dichiaro il registro. Ho già fatto che DispatcherImpl extends UnicastRemoteObjects, quindi ereditarietà, quindi non devo esportare lo stub ma solo rebindare
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IDispatcher dispatcher = new DispatcherImpl();
            rmiRegistry.rebind("dispatcher", dispatcher);
            System.out.println("Hei");
            System.out.println("[DispatcherServer] Servizio correttamente bindato con il nome di <<dispatcher>>");

        }catch(RemoteException e){
            System.err.println("[DispatcherServer] "+e.getMessage());
        }

    }
    
}
