package Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Service.IDispatcher;

public class ServerDispatcher {
    
    public static void main(String[] args) {
        
        try{

            IDispatcher dispatcher = new DispatcherImpl();
            Registry rmiRegistry = LocateRegistry.getRegistry();
            //SICCOME FACCIO PER DELEGA DEVO ESPORTARE IN MANIERA ESPLICITA
            IDispatcher dispatcherStub = (IDispatcher) UnicastRemoteObject.exportObject(dispatcher,0);
            //ORA POSSO BINDARE LO STUB
            rmiRegistry.rebind("dispatcher", dispatcherStub);
            System.out.println("[ServerDispatcher] Dispatcher correttamente bindato con << dispatcher >>");
            
        }catch(RemoteException e){
            System.err.println("[ServerDispatcher] Errore creazione Dispatcher");
        }


    }

}
