package Client;

import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.Vector;

import Whiteboard.*;

public class ObserverImpl extends UnicastRemoteObject implements Observer{
    
    private static final long serialVersionUID = 4L;

    private Whiteboard remoteWhiteboard;

    public ObserverImpl(Whiteboard w) throws RemoteException{
        remoteWhiteboard=w;
    }

    //Implementazione Notify
    //Il server notifica il client che la lavagna è stata updatata
    //Quindi il client richiede il getShapes alla lavagna (getState)

    @Override
    public void observerNotify() throws RemoteException{

        System.out.println("[Observer] Notified! Printing whiteboard content..");

        //invoco getShapes siccome devo updatarmi

        Vector<Shape> shapes = remoteWhiteboard.getShapes();

        for (int i=0;i<shapes.size();i++){
            shapes.get(i).draw();
        }

    }

}
