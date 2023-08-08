package Server;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import java.rmi.RemoteException;
import java.rmi.NotBoundException;

import Service.GestoreSportelli;
import Service.Sportello;

public class SportelloServer {
    
    public static void main(String[]args){

        try{

            Registry registry = LocateRegistry.getRegistry();

            GestoreSportelli gestore = (GestoreSportelli) registry.lookup("gestore");

            SportelloImpl sportello = new SportelloImpl();
            gestore.sottoscrivi(sportello);

            System.out.println("[SportelloServer] Sottoscritto sportello al gestore");
        }catch(RemoteException e){
            e.printStackTrace();
        }catch(NotBoundException e){
            e.printStackTrace();
        }
    }

}
