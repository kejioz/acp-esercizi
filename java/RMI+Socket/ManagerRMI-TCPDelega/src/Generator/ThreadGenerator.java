package Generator;

import java.rmi.RemoteException;
import java.util.Random;

import AlertNotification.AlertNotification;
import Services.IManager;

public class ThreadGenerator extends Thread{
    
    private IManager manager;

    protected ThreadGenerator(IManager manager){
        super();
        this.manager=manager;
        System.out.println("[ThreadGenerator] Avviato");
    }

    @Override
    public void run() {
        
        //Creo un'istanza di Alert Notification
        Random rand = new Random();
        int componentID = (rand.nextInt(5)+1);
        int criticality = (rand.nextInt(3)+1);
        AlertNotification alert = new AlertNotification(componentID,criticality);

        //Effettuo la call RMI
        try{

            manager.sendNotification(alert);
            System.out.println("[ThreadGenerator] Call RMI effettuata con componentID "+componentID+" e criticality "+criticality);

        }catch(RemoteException e){
            System.err.println(e.getMessage());
        }

    }


}
