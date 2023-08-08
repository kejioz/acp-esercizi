package Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Service.Hello;

public class HelloServer {

        //creo l'oggetto HelloServer e faccio il rebind nel registry
    public static void main (String[] args){
        //utilizzo delega, non ereditarietà, quindi devo esportare
        try{
            //creo l'oggetto
            HelloServerImplDel obj = new HelloServerImplDel();

            //creo lo stub
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj,0); //0 vuol dire porta di default, altrimenti la devi cambiare
        
            //richiedo il registry
            Registry registry = LocateRegistry.getRegistry();

            //rebindo
            registry.rebind("Hello",obj);

        }catch(RemoteException e){
            e.printStackTrace();
        }
    
        //invece per ereditarietà non devo esportare
        //    HelloServerImplEr obj = new ServerImplEr();
        //creo registry
        //    Registry registry = LocateRegistry.getRegistry();
        //bindo
        //    registry.rebind("Hello",obj);
    
    }
}
