package Whiteboard;

import java.rmi.*;
import java.util.*;

//Qui implemento i metodi della lavagna da invocare remote

public interface Whiteboard extends Remote{
    
    //Metodo per aggiungere una shape al vettore di shapes. Chiamato dal clientpopulate
    public void addShape (Shape s) throws RemoteException;

    //Metodo di getState del Subject. Chiamato dall'observer
    public Vector getShapes() throws RemoteException;

    //Metodo di attach degli Observer sul Subject. Chiamato dal clientattach
    public void attachObserver (Observer observer) throws RemoteException;

}
