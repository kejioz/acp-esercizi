package Subscriber;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import Service.ISubscriber;

public class SubscriberImpl implements ISubscriber {
    
    private int porta;
    private String nomefile;

    public SubscriberImpl(String nomefile){
        this.nomefile=nomefile;
    }

    @Override
    public void notifyAlert(int criticality) {
        
        try {

            FileWriter fw = new FileWriter(nomefile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(Integer.toString(criticality));
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
            System.out.println("[SUBSCRIBER] IL VALORE DI CRITICALITY "+criticality+" E' STATO SALVATO SU FILE "+nomefile+" CON SUCCESSO");
            
        } catch (Exception e) {
            


        }

        
    }

    public int getPorta() {
        return porta;
    }

    public String getNomefile() {
        return nomefile;
    }

}
