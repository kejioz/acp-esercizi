package Printer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrinter extends Remote {
    
    public void printDoc(String nomeDocumento) throws RemoteException ;

}
