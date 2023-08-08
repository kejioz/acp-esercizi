package Coda;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CodaLock extends CodaWrapper{

    private Lock lock;
    private Condition disp_spazio;
    private Condition disp_dato;
    
    public CodaLock(Coda c){
        super(c);
        lock = new ReentrantLock();
        disp_dato = lock.newCondition();
        disp_spazio = lock.newCondition();

    }

    @Override
    public void inserisci(int p) {
        
        //Acquisisco il lock
        lock.lock();
        try{

            //Se la coda è piena blocco la condition variable
            while (coda.full()){
                disp_spazio.await();
            }

            //Inserisco e notifico che c'è dato
            coda.inserisci(p);
            disp_dato.signalAll();

        }catch(InterruptedException e){

            System.err.println("CodaLock interrotta");

        }finally{
            lock.unlock();
        }
        
    }
    
    @Override
    public int preleva() {

        int dato=-1;

        //Acquisisco il lock
        lock.lock();
        try{

            //Se la coda è vuotablocco la condition variable
            while (coda.empty()){
                disp_dato.await();
            }

            //Prelevo e notifico che c'è spazio
            dato = coda.preleva();
            disp_spazio.signalAll();

        }catch(InterruptedException e){

            System.err.println("CodaLock interrotta");

        }finally{
            lock.unlock();
        }

        return dato;
    }

}
