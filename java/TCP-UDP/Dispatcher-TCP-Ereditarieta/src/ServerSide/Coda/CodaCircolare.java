package ServerSide.Coda;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

//nota : Reentrant Lock evita deadlock

public class CodaCircolare implements Coda{

    private int data[]; //vettore di elementi int

    private int size;   //dimensione massima
    private int elem;   //quanti elementi ci sono aattualmente

    private int testa;  
    private int coda;

    //adesso gestisco i locks
    private Lock l;
    private Condition spa_disp;
    private Condition com_disp;

    //costruttore della Coda, di dimensione 5
    public CodaCircolare(int s){
        size = s;
        elem =0;
        testa=0;
        coda=0;
        data=new int[size];
        l=new ReentrantLock();
        spa_disp = l.newCondition();
        com_disp = l.newCondition();
    }

    //implemento i metodi della coda circolare

    @Override
    public boolean empty(){
        if (elem==0){
            return true;
        }
        return false;
    }

    @Override
    public boolean full(){
        if (elem==size){
            return true;
        }
        return false;
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public void inserisci(int s){
        l.lock();
        //modulo in quanto ho override quando supero size
        try{    
            while(this.full()){
                System.out.println("Coda piena, non posso depositare");
                spa_disp.await();   //await mette il thread corrente in wait fin quando non c'è notify
            }   
            //nelle code circolari si aggiunge sempre in coda
            data[coda%size]=s;
            System.out.println("[Coda] Ho aggiunto alla coda "+s);
            ++coda; //la coda si aggiorna
            ++elem; //aumento numero elementi
            com_disp.signal();//notifico
        }catch(InterruptedException e){
            System.err.println("Eccezione di interruzione");
        }finally{
            l.unlock(); 
        }
    }

    @Override
    public int preleva(){
        //nelle code circolare si preleva sempre in testa
        l.lock();
        int x=0;
        try{
            while(this.empty()){
                System.out.println("Coda vuota, non posso prelevare");
                com_disp.await();
            }
                x= data[testa%size];
                ++testa; //la testa si aggiorna
                --elem; //decremento numero elementi
                System.out.println("Rimosso dalla coda");
                com_disp.signal();
        }catch(InterruptedException e){
            System.err.println("Eccezione di interruzione");
        }finally{
            l.unlock();
        }
        return x;    
    }

}