package Manager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import AlertNotification.AlertNotification;

public class ManagerImpl extends UnicastRemoteObject implements IManager{

    public ArrayList <SubscriberProxy> subscribers;
    
    public ManagerImpl() throws RemoteException{
        subscribers = new ArrayList<SubscriberProxy>();
    }

    @Override
    public void sendNotification(AlertNotification alert) throws RemoteException {
        
        int componentID = alert.getComponentID();
        int criticality = alert.getCriticality();

        for (int i=0;i<subscribers.size();i++){
            if ((subscribers.get(i).getComponentID())==componentID){
                subscribers.get(i).notifyAlert(criticality);
                System.out.println("[Manager] Subscriber notificato per il componente "+componentID+" e criticalità "+criticality);
            }
        }

        
    }

    @Override
    public void subscribe(int componentID, int porta) throws RemoteException {
        
        SubscriberProxy subscriber = new SubscriberProxy(porta, componentID);
        subscribers.add(subscriber);
        System.out.println("[Manager] Subscriber aggiunto per il component id "+componentID+". Lo contatterò su porta "+porta);

        
    }

}
