public class CodaSynch extends CodaWrapper {

    public CodaSynch(Coda c){
        super(c);
    }


    @Override
    public void inserisci(int s) {
        
        synchronized(coda){
        while(coda.full()){
            try{
                coda.wait(); //blocco sulla condition
            } catch (InterruptedException e){
                System.err.println("Bloccato!");
            }
        }

        coda.inserisci(s);
        System.out.println("[Synch] Ho prodotto");
        coda.notifyAll(); //notifico tutti!
        }
        
    }



    @Override
    public int preleva() {

        synchronized(coda){
        while(coda.empty()){
            try{
                coda.wait(); //blocco sulla condition
            } catch (InterruptedException e){
                System.err.println("Bloccato!");
            }
        }

        int x = coda.preleva();
        System.out.println("[Synch] Ho prelevato");
        coda.notifyAll(); //notifico tutti!

        return x;
        }
    }
    
}