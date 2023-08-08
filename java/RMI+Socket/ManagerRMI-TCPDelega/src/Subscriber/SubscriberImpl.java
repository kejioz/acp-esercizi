package Subscriber;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;

import Services.IManager;
import Services.ISubscriber;

public class SubscriberImpl implements ISubscriber {

    private int porta;
    private String nomefile;
    private IManager manager;

    protected SubscriberImpl(int idComponente,String nomefile,IManager manager,int porta){

        this.nomefile=nomefile;
        this.manager=manager;
        this.porta=porta;
        System.out.println("[SubscriberImpl] Inizializzato..richiesta di sottoscrizione");
        try {
            this.manager.subscribe(idComponente, this.porta);
        } catch (RemoteException e) {
            System.err.println("[SubscriberImpl"+e.getCause());
        }
    
    }
    
    @Override
    public void notifyAlert(int criticality) {
        
        System.out.println("[SubscriberImpl] Il livello di criticita è "+criticality);
        try{

            FileWriter fw = new FileWriter(nomefile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(criticality);
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
            System.out.println("[SubscriberImpl] Criticità "+criticality+" salvata sul file "+nomefile);

        }catch(IOException e){
            System.err.println(e.getMessage());
        }
        
    }


}
