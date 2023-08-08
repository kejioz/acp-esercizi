package Coda;

public abstract class CodaWrapper implements Coda {
    
    protected Coda coda;

    protected CodaWrapper (Coda c){
        this.coda=c;
    }

    @Override
    public boolean full() {
        return coda.full();
    }

    @Override
    public boolean empty() {
        return coda.empty();
    }

    @Override
    public int getSize() {
        return coda.getSize();
    }

}
