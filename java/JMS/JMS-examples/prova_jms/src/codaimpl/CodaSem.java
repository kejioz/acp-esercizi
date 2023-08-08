package codaimpl;
import coda.*;
import java.util.concurrent.Semaphore;



public class CodaSem extends CodaWrapper {

    private Semaphore spa_disp;
    private Semaphore mes_disp;

    public CodaSem(Coda c){
        super(c);
        spa_disp = new Semaphore(coda.getSize());
        mes_disp = new Semaphore(0);
    }


    @Override
    public void inserisci(int s) {

        //acquisisco
        try{
        spa_disp.acquire(); //prendo spa_disp

            //dopo aver acquisito faccio inserisci 
            synchronized(coda){
                coda.inserisci(s); //inserisoc in mutua esclusione (ricorda MUTEXP)
                System.out.println("[Sem] Ho prodotto!");
            }
        
        mes_disp.release();

        } catch(InterruptedException e){
            System.err.println("Bloccato!");
        }
        
    }
    
    @Override
    public int preleva() {

        int x = 0;
        
        try{

        mes_disp.acquire();
            
        synchronized(coda){
            x=coda.preleva();
            System.out.println("[Sem]Ho prelevato!");
        }

        spa_disp.release();

        } catch (InterruptedException e){
            System.err.println("Bloccato!!");
        }

        return x;
    }
}