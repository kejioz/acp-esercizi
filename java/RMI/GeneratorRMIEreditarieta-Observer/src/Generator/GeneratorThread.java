package Generator;

import java.rmi.RemoteException;
import java.util.Random;

import Reading.Reading;
import Service.IDispatcher;

public class GeneratorThread extends Thread{

    private IDispatcher dispatcher;

    protected GeneratorThread(IDispatcher dispatcher){
        this.dispatcher=dispatcher;
        System.out.println("[GeneratorThread] Inizializzato");
    }

    @Override
    public void run() {
        
        //Devo eseguire 3 invocazioni di setReading
        for (int i=0;i<3;i++){

            try{

                //Preparazione random del tipo e del valore
                double random= Math.random();
                String tipo;
                if (random<0.5){
                    tipo = "temperatura";
                }else{
                    tipo= "pressione";
                }
                Random rand = new Random();
                int valore = rand.nextInt(51);

                //Creo l'istanza random di Reading
                Reading reading = new Reading(tipo,valore);

                //Faccio la call al dispatcher
                dispatcher.setReading(reading);
                System.out.println("[GeneratorThread] Chiamato il setReading con il tipo "+tipo+" ed il valore "+valore);

                //Aspetto 5 secondi
                Thread.sleep(3000);

            }catch(InterruptedException e){
                System.err.println("[GeneratorThread] "+e.getMessage());
            }catch(RemoteException e){
                System.err.println("[GeneratorThread] "+e.getMessage());
            }

        }

    }


    
}
