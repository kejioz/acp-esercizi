package Alert;

import java.io.Serializable;

public class AlertNotification implements Serializable{
    
    private int idComponente;
    private int criticality;

    public AlertNotification(int idComponente,int criticality){
        this.idComponente = idComponente;
        this.criticality = criticality;
    }

    public int getCriticality() {
        return criticality;
    }

    public int getIdComponente() {
        return idComponente;
    }

}
