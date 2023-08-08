package Service;

//importo metodi per Remote

import java.rmi.Remote;
import java.rmi.RemoteException;


//dichiaro l'interfaccia Hello che estende Remote
//quindi posso evocare i metodi da una macchina non locale
public interface Hello extends Remote{

    public String sayHello() throws RemoteException;

}