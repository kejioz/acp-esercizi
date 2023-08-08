package Coda;

import java.util.concurrent.Semaphore;

public class CodaCircolare implements Coda{

    private String dati[];
    private int size;
    private int head;
    private int tail;
    private int elems;

    private Semaphore spaziodisp;
    private Semaphore messdisp;

    public CodaCircolare (int dimensione){
        size = dimensione;
        dati = new String[size];
        head=0;
        tail=0;
        elems=0;

        spaziodisp = new Semaphore ( this.getSize() ); //inizializzo ad n permessi il semaforo spazio
        messdisp = new Semaphore (0); //inizializzo a 0 permessi il semaforo mess

    }

    @Override
    public boolean vuota() {
        
        if (elems==0){
            return true;
        }
        return false;

    }

    @Override
    public boolean piena() {
        
        if (elems==size){
            return true;
        }
        return false;
        
    }

    @Override
    public void inserisci(String comando) {
        
        try{
            spaziodisp.acquire();
            synchronized(this){
                dati[tail%size]=comando;
                elems++;
                tail++;
            }

            messdisp.release();

        }catch(InterruptedException e){
            System.err.println("[CodaCircolare] Bloccato!");
        }
        
    }

    @Override
    public String preleva() {
        
        String cmd="";

        try{
            messdisp.acquire();

            synchronized(this){
                cmd = dati[head%size];
                elems--;
                head++;
            }

            spaziodisp.release();

        }catch(InterruptedException e){
            System.err.println("[CodaCircolare] Bloccato!");
        }

            return cmd;

    }

    @Override
    public int getSize() {
        return size;
    }
    
}
