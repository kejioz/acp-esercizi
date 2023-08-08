package Subscriber;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SubscriberImpl implements ISubscriber{
    
    private String nomefile;

    public SubscriberImpl(String nomefile){
        this.nomefile=nomefile;
    }

    @Override
    public synchronized void notifyAlert(int criticality) {

        try{

            System.out.println("[Subscriber] Ricevuto valore di criticality "+criticality);

            //File
            FileWriter fw = new FileWriter(nomefile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(Integer.toString(criticality));
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
            System.out.println("[Subscriber] Salvata "+criticality+" con successo su file "+nomefile);

        }catch(IOException e){
            e.printStackTrace();
        }

    }

}
