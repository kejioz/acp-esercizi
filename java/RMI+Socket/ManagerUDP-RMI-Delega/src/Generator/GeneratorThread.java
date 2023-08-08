package Generator;

import java.util.Random;
import Alert.AlertNotification;
import Service.IManager;

public class GeneratorThread extends Thread{
    
    private IManager manager;

    protected GeneratorThread(IManager manager){
        super();
        this.manager = manager;
    }
    
    @Override
    public void run() {
       
        try {
            
            //Generato alert
            AlertNotification alert = new AlertNotification(new Random().nextInt(5) + 1, new Random().nextInt(3) + 1);

            System.out.println(("[THREAD] INVIO AL MANAGER UN ALERT CON ID "+alert.getIdComponente()+" E CRITICALITY "+alert.getCriticality()).toLowerCase());
            manager.sendNotification(alert);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
