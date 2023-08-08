package Client;

import Whiteboard.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

import Whiteboard.*;

public class ClientAttach {
    
    public static void main(String[] args) {
        
        try{

            Registry rmiRegistry = LocateRegistry.getRegistry();
            Whiteboard whiteboard = (Whiteboard) rmiRegistry.lookup("whiteboard");

            Observer observer = new ObserverImpl(whiteboard);

            System.out.println("[ClientAttach] Attaching Observer...");
            whiteboard.attachObserver(observer);

        }catch(NotBoundException e){
            System.err.println("[ClientAttach]Not bound exception");
        }catch(RemoteException e){
            System.err.println("[ClientAttach]Remote exception");
        }

    }

}
