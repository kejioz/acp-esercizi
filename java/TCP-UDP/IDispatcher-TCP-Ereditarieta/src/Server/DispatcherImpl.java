package Server;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class DispatcherImpl extends DispatcherSkeleton {

    //Sezione Coda
    private int data[];
    private int size;
    private int elems;
    private int head;
    private int tail;

    //Sezione Locks
    private ReentrantLock l;
    private Condition prelevabile;
    private Condition depositabile;
    
    //Costruttore

    public DispatcherImpl(int s){
        size=s;
        elems=0;
        head=0;
        tail=0;
        data=new int[size];
        l=new ReentrantLock();
        prelevabile = l.newCondition();
        depositabile = l.newCondition();
    }

    //Metodi utility per la coda
    public boolean empty(){
        if (elems==0){
            return true;
        }
        return false;
    }

    public boolean full(){
        if (elems==size){
            return true;
        }
        return false;
    }

    public int getSize(){
        return elems;
    }

    @Override 
    public void sendCmd(int c){
        l.lock();   //acquisisco il lock sull'oggetto
        try{
            while(this.full()){ //se è full allora attendo che ci sia spazio, che sia depositabile
                System.out.println("[Dispatcher] Coda piena...mettendo in wait..");
                depositabile.await();
            }
            data[tail%size]=c;
                System.out.println("[Dispatcher] Ho aggiunto in coda "+c);
            elems++;
            head++;
            prelevabile.signal();   //una volta prodotto notifico che è anche prelevabile
        }
        catch(InterruptedException e){
            System.err.println("[Dispatcher]Errore interruzione");
        }finally{
            l.unlock(); //ad ogni modo rimuovo il lock
        }
    }

    @Override
    public int getCmd(){
        int x=-1;
        l.lock();
        try {
            while (this.empty()){   //se è vuoto l'oggetto allora aspetto che sia prelevabile
                System.out.println("[Dispatcher] Coda vuota...mettendo in wait..");
                prelevabile.await();
            }
            x=data[head%size];
            System.out.println("[Dispatcher] Ho rimosso dalla coda "+x);
            head++;
            elems--;
            depositabile.signal();
            return x;
        } catch (InterruptedException e) {
            System.err.println("[Dispatcher]Errore interruzione");
        }finally{
            l.unlock();
        }
        return x;
    }
}
