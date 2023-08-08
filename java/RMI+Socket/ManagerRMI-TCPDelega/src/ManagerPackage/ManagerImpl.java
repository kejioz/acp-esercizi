package ManagerPackage;

import java.rmi.RemoteException;
import java.util.ArrayList;

import AlertNotification.*;
import Services.*;


public class ManagerImpl implements IManager{

    private ArrayList <SubscriberProxy> subscribers;

    public ManagerImpl(){
        subscribers=new ArrayList<SubscriberProxy>();
    }
    
    @Override
    public void subscribe(int idComponente, int porta) throws RemoteException {
        
        SubscriberProxy proxy = new SubscriberProxy (porta,idComponente);
        subscribers.add(proxy);
        System.out.println("[ManagerImpl] Nuovo subscriber aggiunto su componente "+idComponente+" e porta "+porta);
        
    }

    @Override
    public synchronized void sendNotification(AlertNotification alert) throws RemoteException {
        
        //Salvo il valore del componentid
        int idComponenteRicevuto = alert.getIdComponente();
        int criticalityRicevuta = alert.getCriticality();
        System.out.println("[ManagerImpl] Ho ricevuto un alert per il componente "+idComponenteRicevuto+" di criticità "+criticalityRicevuta);

        //Scorro il vettore di subscribers
        for (int i=0;i<subscribers.size();i++){

            System.out.println(subscribers.get(i).getIdComponente());
            //Se esistono subscribers per quel componente faccio la notifyAlert
            if (subscribers.get(i).getIdComponente() == idComponenteRicevuto){

                subscribers.get(i).notifyAlert(criticalityRicevuta);

            }

        }
        
    }

}
