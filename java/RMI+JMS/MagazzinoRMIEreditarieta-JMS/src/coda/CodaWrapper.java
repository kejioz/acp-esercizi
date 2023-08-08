package coda;

public class CodaWrapper implements Coda {

    protected Coda queue;

    public CodaWrapper(Coda c){
        queue = c;
    }

    @Override
    public void inserisci(int a) {
        queue.inserisci(a);
    }

    @Override
    public int preleva() {
        int a =queue.preleva();
        return a;
    }

    @Override
    public boolean full() {
        return queue.full();
    }

    @Override
    public boolean empty() {
        return queue.empty();
    }

    
}
