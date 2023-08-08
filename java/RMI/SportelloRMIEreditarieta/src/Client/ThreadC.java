package Client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Random;

import Server.GestoreSportelliImpl;
import Service.GestoreSportelli;

public class ThreadC extends Thread {

    private int reqs;
    private GestoreSportelli gestore;

    public ThreadC(int r,GestoreSportelli gest){
        reqs=r;
        gestore=gest;
    }   
        
    public void run(){

        System.out.println("[ThreadC] Nuovo Thread avviato..");

        Random rand= new Random();

        for (int i=0;i<reqs;i++){
            try{
                Thread.sleep((rand.nextInt(3)+1)*1000);
                System.out.println("[TreadC] HO dormito");
                int idCliente = (rand.nextInt(100))+1;
                boolean result=gestore.sottoponiRichiesta(idCliente);
                System.out.println("[ThreadC] Richiesta servita con esito"+((result==true)?"positivo":"negativo"));
            }catch(RemoteException e){
                System.err.println("Errore RemoteException in ThreadC "+this.getName());
            }catch(InterruptedException e){
                System.err.println("Errore Interrupted in thread "+this.getName());
            }
        }

    }

}
