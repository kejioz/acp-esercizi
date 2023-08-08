package Server;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import Whiteboard.*;

public class WhiteboardServer {
    
    public static void main(String[] args){

        try{    
            //Creo l'oggetto Whiteboard, che bindo al mio registro
            //Permetto quindi l'invocazione dei metodi sull'oggetto
            System.out.println("[WhiteboardServer]Creating the object...");
            Whiteboard whiteboard = new WhiteboardImpl();

            //Creo il registro e lo bindo
            Registry rmiRegistry = LocateRegistry.getRegistry();
            rmiRegistry.rebind("whiteboard",whiteboard);
            System.out.println("[WhiteboardServer]Object registered with name 'whiteboard'...");
        }catch (AccessException e){
            System.err.println("[WhiteboardServer]Error in rebinding registry");
        }catch(RemoteException e){
            System.err.println("[WhiteboardServer]Error in getting registry");
        }

    }

}
