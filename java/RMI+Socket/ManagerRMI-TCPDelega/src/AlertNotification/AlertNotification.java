package AlertNotification;

import java.io.Serializable;

public class AlertNotification implements Serializable {
    
    private int idComponente;
    private int criticality;

    public AlertNotification(int idComponente, int criticality){
        this.idComponente=idComponente;
        this.criticality=criticality;
    }

    //Getters
    public int getIdComponente(){
        return this.idComponente;
    }

    public int getCriticality(){
        return this.criticality;
    }

}
