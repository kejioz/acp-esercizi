package Service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Reading.Reading;

public interface IDispatcher extends Remote{
    
    public void setReading(Reading reading) throws RemoteException;

    public Reading getReading() throws RemoteException;

    public void attachObserver (Observer observer,String tipo)throws RemoteException;

}
