package codaimpl;
import coda.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CodaLock extends CodaWrapper {

    private Lock l;
    private Condition spa_disp;
    private Condition mes_disp;

    public CodaLock(Coda c){
        super(c);  //richiamno costruttore 
        l= new ReentrantLock(); //generop reentrant lock
        spa_disp = l.newCondition(); //genero le condition variable
        mes_disp = l.newCondition();
    }



    @Override
    public void inserisci(int s) {

        l.lock();

        try{
            while(coda.full()){
                System.out.println("[Lock] Coda piena!");
                spa_disp.await();
            }

            coda.inserisci(s); //chiamo il metodo ponendo la sincronizazione
            mes_disp.signal(); //segnalo vartiabile condition per consumatori
        } catch (InterruptedException e){ 
            System.err.println("Interrotto!");
        } finally{
            l.unlock(); //di default
        }


        
    }



    @Override
    public int preleva() {
        int x=0;
        l.lock();
        

        try{
            while(coda.empty()){
                System.out.println("[Lock] Coda vuota!");
                mes_disp.await();
            }

            x = coda.preleva(); //chiamo il metodo ponendo la sincronizazione
            spa_disp.signal(); //segnalo vartiabile condition per consumatori
        } catch (InterruptedException e){ 
            System.err.println("Interrotto!");
        } finally{
            l.unlock(); //di default
        }

        return x; //ritorno valore consumato
        
    }
    
}