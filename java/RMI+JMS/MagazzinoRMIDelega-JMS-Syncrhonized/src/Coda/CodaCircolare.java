package Coda;

public class CodaCircolare implements Coda {
    
    private int[] data;
    private int head;
    private int tail;
    private int elems;
    private int size;

    public CodaCircolare(int size){
        this.size=size;
        data = new int[size];
        head=0;
        tail=0;
        elems=0;
    }

    public int getSize(){

        return size;

    }

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

    @Override
    public void inserisci(int p) {
        
        data[tail%size]=p;

        elems++;
        tail++;

    }

    @Override
    public int preleva() {
        
        int dato = data[head%size];

        elems--;
        head++;

        return dato;

    }

}
