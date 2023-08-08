
public abstract class CodaWrapper implements Coda{

    //classe astratta per la gestione della coda, implemento solo metodi di getsize
    //empty e full

    protected Coda coda;

    public CodaWrapper(Coda c){
        coda = c;
    }

    @Override
    public int getSize() {
        return coda.getSize();
    }


    @Override
    public boolean empty() {
            return coda.empty();
    }

    @Override
    public boolean full() {
        return coda.full(); //chiamo i metodi sostanzialmente
    }

    


}