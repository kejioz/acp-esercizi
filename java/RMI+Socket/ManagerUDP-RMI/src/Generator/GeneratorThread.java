package Generator;

import java.rmi.RemoteException;
import java.util.Random;

import AlertNotification.AlertNotification;
import Manager.IManager;

public class GeneratorThread extends Thread {
    
    private IManager manager;

    protected GeneratorThread(IManager manager){
        this.manager = manager;
        System.out.println("[Thread] Avviato");
    }

    @Override
    public void run() {
        
        try{

            Random rand = new Random();
            //Creo l'alert notification
            AlertNotification alert = new AlertNotification((rand.nextInt(5)+1),(rand.nextInt(3)+1));

            //Evoco il metodo sendNotification del manager
            manager.sendNotification(alert);
            System.out.println("[Thread] Alert con id "+alert.getComponentID()+" e criticità "+alert.getCriticality()+" mandato ");
            
        }catch(RemoteException e){
            e.printStackTrace();
        }


    }

}
