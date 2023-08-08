package Service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Server.PrinterSkeletonDelega;

public interface IDispatcher extends Remote {

    public void addPrinter(PrinterSkeletonDelega printer) throws RemoteException; //attach

    public boolean printRequest(String docName) throws RemoteException;

}