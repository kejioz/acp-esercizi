package Dispatcher;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.jms.*;

import Service.IPrinter;

public class DispatcherThread implements Runnable{
    
    private MapMessage message;
    private String nomeDocumento;
    private String nomePrinter;

    public DispatcherThread(Message mess){
            message = (MapMessage) mess;
    }

    @Override
    public void run() {
        
        try{

            this.nomeDocumento = message.getString("nomeDocumento");
            this.nomePrinter = message.getString("nomePrinter");
            System.out.println("[DispatcherThread] Il nome del documento è "+nomeDocumento+" da inoltrare alla printer "+nomePrinter);

            Registry rmiRegistry = LocateRegistry.getRegistry();
            IPrinter printer = (IPrinter)rmiRegistry.lookup(nomePrinter);
            printer.printDoc(nomeDocumento);
            System.out.println("[DispatcherThread] effettuata upcall al registro rmi");


        }catch(JMSException e){
            System.err.println("[DispatcherThread] JMSException");
        }catch(RemoteException e){
            System.err.println("[DispatcherThread] RemoteException");
        }catch(NotBoundException e){
            System.err.println("[DispatcherThread] NotBoundException");
        }


    }

}
