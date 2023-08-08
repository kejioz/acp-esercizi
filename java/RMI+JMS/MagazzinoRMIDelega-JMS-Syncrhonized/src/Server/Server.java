package Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server {
    
    public static void main(String[] args) {
        
        try{

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IServer server = new ServerImpl();
            rmiRegistry.rebind("server",server);
            System.out.println("[Server] Avviato e bindato il registro col nome / server /");

        }catch(RemoteException e){
            System.err.println("[Server] RemoteException "+e.getMessage());
        }        

    }

}
