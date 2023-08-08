package coda;

public class CodaCircolare implements Coda{
    
    private int data[];
    private int size;
    private int head;
    private int tail;
    private int elems;

    //Costruttore
    public CodaCircolare(int s){
        size=s;
        head=0;
        tail=0;
        elems=0;
        data=new int[size];
    }

    public int getSize(){
        return elems;
    }

    @Override
    public void inserisci(int a) {
        data[tail%size]=a;
        System.out.println("[CodaCircolare] Ho prodotto "+a);
        tail++;
        elems++;        
    }

    @Override
    public int preleva() {
        int a = data[head%size];
        elems--;
        head++;
        System.out.println("[CodaCircolare] Ho consumato "+a);
        return a;
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


}
