package Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Service.GestoreSportelli;
import Server.GestoreSportelliImpl;

public class Client {
  
    public static void main(String[]args){

        int T = 10;
        int R = 10;

        try{
            Registry registry = LocateRegistry.getRegistry();
            GestoreSportelli gestore = (GestoreSportelli) registry.lookup("gestore");
            ThreadC[] workers = new ThreadC[T];

            for (int i=0;i<T;i++){
                workers[i]=new ThreadC(R, gestore);
                workers[i].start();
            }

        }catch(RemoteException e){
            System.err.println("Errore remote Exception Clien");
        }catch(NotBoundException e){
            System.err.println("Errore NotBound in Client");
        }

    }
}
