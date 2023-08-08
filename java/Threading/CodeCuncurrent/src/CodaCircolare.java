public class CodaCircolare implements Coda{


    private int data [];

    private int size;
    private int elem;

    private int testa;
    private int coda;


    public CodaCircolare(int s){
        size = s; //pongo la size prescelta 
        elem =0; //elementi che ci sono 
        testa = 0; //testa e coda 
        coda =0;
        data = new int[size]; //creo nuovo vettore di size elementi
    }

    //qui implemento i metodi in generale senza protezione

    @Override
    public boolean empty() {
        if(elem == 0){
            return true;
        }
        return false;
    }

    //funzioni di full e di empty 
    @Override
    public boolean full() {
        if(elem == size){
            return true;
        }
        return false;
    }

    @Override
    public int getSize() {
        return size;
    }

    //ora metodi produci e consuma ma senza gestione concorrente 


    @Override
    public void inserisci(int s) {
        //come inserisco 

        data[coda%size]= s; //numero casuale castato in intero
        
        System.out.println("[Produttore] Ho prodotto: "+ s);

        ++coda;
        ++elem; //incremento numero di elementi!

    }


    @Override
    public int preleva() {
    
            int x = data[testa%size];

            System.out.println("[Consumatore] Ho consumato!");

            ++testa;
            --elem;

            return x;
    }

    
}