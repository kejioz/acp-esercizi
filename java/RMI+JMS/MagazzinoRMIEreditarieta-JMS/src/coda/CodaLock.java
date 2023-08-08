package coda;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CodaLock extends CodaWrapper {
    
    private ReentrantLock l;
    private Condition depositabile;
    private Condition prelevabile;

    public CodaLock(Coda c){
        super(c);
        l=new ReentrantLock();
        depositabile=l.newCondition();
        prelevabile=l.newCondition();
    }

    @Override
    public void inserisci(int a) {
        
        l.lock();

        try{
            while (queue.full()){
                System.out.println("[CodaLock] Coda piena! In attesa...");
                depositabile.await();
            }

            queue.inserisci(a);
            System.out.println("[CodaLock] Inserito elemento "+a+" nella coda! Ora prelevabile..");
            prelevabile.signal();

        }catch(Exception e){
            System.err.println("[CodaLock] Eccezione "+e.getCause());
        }finally{
            l.unlock();
        }

    }

    @Override
    public int preleva() {
        
        l.lock();
        int a=0;
        try{
            while(queue.empty()){
                prelevabile.await();
                System.out.println("[CodaLock] Coda vuota! In attesa..");
            }
            a= queue.preleva();
            System.out.println("[CodaLock] Prelevato elemento "+a);
            depositabile.signal();
        }catch(Exception e){
            System.err.println("[CodaLock] Errore");
        }finally{
            l.unlock();
        }
        return a;
    }

}
