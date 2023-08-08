package Observer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Service.IDispatcher;
import Service.Observer;

public class ObserverClient {
    
    public static void main(String[] args) {
        try{

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IDispatcher dispatcher = (IDispatcher) rmiRegistry.lookup("dispatcher");
            System.out.println("[ObserverClient] Lookup effettuata con successo a <<dispatcher>>");
            Observer observer = new ObserverImpl(dispatcher,args[0],args[1]);
            System.out.println("[ObserverClient] Creato observer di tipo "+args[0]+" che lavora sul file "+args[1]);
            dispatcher.attachObserver(observer, observer.getTipo());
            System.out.println("[ObserverClient] Observer attaccato");

        }catch(RemoteException e){
            System.err.println("[ObserverClient] RemoteException ");
        }catch(NotBoundException e){
            System.err.println("[ObserverClient] NotBoundException ");
        }


    }

}
