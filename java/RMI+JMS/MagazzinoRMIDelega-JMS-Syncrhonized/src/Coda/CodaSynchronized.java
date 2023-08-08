package Coda;

public class CodaSynchronized extends CodaWrapper {
    
    protected CodaSynchronized (Coda c){
        super(c);
    }

    @Override
    public synchronized void inserisci(int p) {
        
        try{
            //Sono nella coda. Se è full waito.
            while(coda.full()){
                wait();
            }

            coda.inserisci(p);
            notifyAll();


        }catch(InterruptedException e){
            System.err.println("CodaSynchr interrotta");
        }

    }

    @Override
    public synchronized int preleva() {
        
        int dato =-1;

        try{
            //Sono nella coda. Se è full waito.
            while(coda.empty()){
                wait();
            }

            dato = coda.preleva();
            notifyAll();


        }catch(InterruptedException e){
            System.err.println("CodaSynchr interrotta");
        }

        return dato;

    }

}
