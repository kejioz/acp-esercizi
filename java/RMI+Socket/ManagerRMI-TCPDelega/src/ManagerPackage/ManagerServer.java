package ManagerPackage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Services.*;

public class ManagerServer{
    
    public static void main(String[] args) {
        
        System.out.println("[ManagerServer] Avviato...");

        try{

            //Binding del servizio sul registro
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IManager manager = new ManagerImpl();
            IManager managerStub = (IManager) UnicastRemoteObject.exportObject(manager,0);  //Esporto perchè uso delega
            rmiRegistry.rebind("manager", managerStub); //Bindato servizio
            System.out.println("[ManagerServer] Servizio RMI bindato con il nome << manager >>");

        }catch(RemoteException e){
            System.out.println("[ManagerServer] "+e.getMessage());
        }

    }

}
