package Whiteboard;

import java.rmi.*;

//E' un'interfaccia di Callback. Il server chiama questi
//metodi remoti per notificare gli observer
public interface Observer extends Remote{
    
    //Metodo di Notify invocabile dal server sugli Observers
    public void observerNotify() throws RemoteException;

}
