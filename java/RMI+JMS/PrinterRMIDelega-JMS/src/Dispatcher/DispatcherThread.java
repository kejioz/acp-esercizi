package Dispatcher;

import Service.IPrinter;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.jms.JMSException;
import javax.jms.MapMessage;

public class DispatcherThread extends Thread {

    private MapMessage mapmessage;
    
    public DispatcherThread(MapMessage message){
        this.mapmessage=message;
    }

    public void run(){

        try{

            //Estraggo nomeDocumento e nomePrinter e faccio sysout
            String nomeDocumento = mapmessage.getString("nomeDocumento");
            String nomePrinter = mapmessage.getString("nomePrinter");
            System.out.println("[Dispatcher"+Thread.currentThread().getName()+"] Il nome del documento è "+nomeDocumento);

            //Ora cerco il servizio dell'rmiRegistry
            Registry rmiRegistry = LocateRegistry.getRegistry();
            IPrinter printer = (IPrinter) rmiRegistry.lookup(nomePrinter);
            System.out.println("[Dispatcher"+Thread.currentThread().getName()+"] Lookup della printer " +nomePrinter+ " effettuata con successo");

            //Evoco il metodo sulla printer
            printer.printDoc(nomeDocumento);
            System.out.println("[Dispatcher"+Thread.currentThread().getName()+"] Evocata la print di " +nomeDocumento+ " sulla printer " +nomePrinter);

        }catch(JMSException e){
            System.err.println("[Dispatcher"+Thread.currentThread().getName()+"] JMSException");
        }catch(RemoteException e){
            System.err.println("[Dispatcher"+Thread.currentThread().getName()+"] RemoteException");
        }catch(NotBoundException e){
            System.err.println("[Dispatcher"+Thread.currentThread().getName()+"] NotBOundException");
        }

    }

}
