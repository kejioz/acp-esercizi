package Reading;


import java.io.Serializable;


public class Reading implements Serializable{
    
    private String tipo;
    private int valore;

    public Reading(String tipo,int valore){
        this.tipo=tipo;
        this.valore=valore;
    }

    public String getTipo(){
        return this.tipo;
    }

    public int getValore(){
        return this.valore;
    }

}
