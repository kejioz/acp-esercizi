package Client;

import java.rmi.RemoteException;
import java.util.Random;

import Service.IDispatcher;

public class NodeThread extends Thread {

    //Dispatcher a cui devo fare riferimento
    private IDispatcher dispatcher;
    
    //Costruttore di NodeThread a cui passo il dispatcher. DEVO CAPIRE SE EFFETTIVAMENTE PASSO IL RIFERIMENTO ALL'OGGETTO REMOTO, OPPURE QUELLO ALL'OGGETTO NON REMOTO CHE SI RIFERISCE AL REMOTO
    protected NodeThread (IDispatcher disp){
        dispatcher=disp;
    }

    //Metodo di run, cosa deve fare il thread
    @Override
    public void run() {

        System.out.println("[NodeThread] Nuovo Thread avviato...");

        Random rand = new Random();

        //Ogni 3 secondi invocco una printRequest con un docName diverso
        for (int i=0;i<3;i++){

            //docnameRandom
            Integer random = rand.nextInt(50)+1;
            String docName = new String("doc"+random.toString());
            
            try{

                //Invoco la print sul dispatcher passando docname
                System.out.println("[NodeThread] Invoco la richiesta di print al dispatcher...");
                boolean ok=dispatcher.printRequest(docName);
                //Sleepo per 3 secondi
                sleep(3000);
                System.out.println("[NodeThread] Valore tornato "+ok);

            }catch(RemoteException e){
                System.err.println("[NodeThread] Errore Remote");
            }catch(InterruptedException e){
                System.err.println("[NodeThread] Errore sleep");
            }

        }

    }

}
