package Server;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import Service.GestoreSportelli;

public class ServerGestore {

    public static void main(String[] args){

        try{

            GestoreSportelli gestore= new GestoreSportelliImpl();

            Registry registry = LocateRegistry.getRegistry();

            registry.rebind("gestore",gestore);

        }catch(RemoteException e){
            e.printStackTrace();
        }

    }
    
}
