package Coda;

public class CodaCircolare implements Coda{
    
    private int [] articoli;
    private int size;
    private int head;
    private int tail;
    private int elems;

    public CodaCircolare(int size){

        this.size=size;
        articoli= new int [size];
        this.head=0;
        this.tail=0;
        this.elems=0;

    }

    @Override
    public boolean empty() {
        
        if (elems==0){
            return true;
        }
        return false;

    }

    @Override
    public boolean full() {
        
        if (elems==size){
            return true;
        }
        return false;

    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void inserisci(int articolo) {
        
        articoli[tail%size]=articolo;

        tail++;
        elems++;

    }

    @Override
    public int  preleva() {
        
        int articolo = articoli[head%size];

        head++;
        elems--;

        return articolo;

    }



}
