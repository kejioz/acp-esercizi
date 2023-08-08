package Manager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import Alert.AlertNotification;
import Service.IManager;

public class ManagerImpl extends UnicastRemoteObject implements IManager{

    private ArrayList<SubscriberProxy> subscribers;

    public ManagerImpl() throws RemoteException{

        subscribers = new ArrayList<SubscriberProxy>();

    }

    @Override
    public void subscribe(int idComponente, int porta) {
        
        SubscriberProxy subscriber = new SubscriberProxy(porta, idComponente);
        subscribers.add(subscriber);
        System.out.println("[MANAGER] RICEVUTA RICHIESTA DI SUBSCRIBE PER IL COMPONENTE "+idComponente+". CONTATTERÒ IL SUBSCRIBER SULLA PORTA "+porta);

    }

    @Override
    public synchronized void sendNotification(AlertNotification alert) {
        
        System.out.println("[MANAGER] RICEVUTA RICHIESTA DI sendNotification, CONTROLLO SE CI SONO SUBSCRIBERS PER QUELL'ID..");

        int idComponente = alert.getIdComponente();
        int criticality = alert.getCriticality();

        for (int i=0;i<subscribers.size();i++){

            if (subscribers.get(i).getIdComponente()==idComponente){

                subscribers.get(i).notifyAlert(criticality);
                System.out.println("[MANAGER] SUBSCRIBER AL COMPONENTE "+idComponente+" NOTIFICATO CON SUCCESSO DI CRITICALITY "+criticality);

            }

        }
        
    }
    
}
