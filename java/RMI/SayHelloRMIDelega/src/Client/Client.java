package Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Service.Hello;

public class Client {
    
    public static void main (String[] args){

        try{

            //prendo registro
            Registry registry = LocateRegistry.getRegistry();

            //effettuo lookup
            Hello stub_cli = (Hello) registry.lookup("Hello");

            //metodo

            String res = stub_cli.sayHello();

            System.out.println("Chiamata: "+res);

        }catch(RemoteException e){
            System.err.println("Errore Remote");
        }catch (NotBoundException e){
            System.err.println("Errore not BOUND!");
        }

    }

}
