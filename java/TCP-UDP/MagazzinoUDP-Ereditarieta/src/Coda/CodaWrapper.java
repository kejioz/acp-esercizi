package Coda;

public abstract class CodaWrapper implements Coda{
    
    protected Coda coda;

    public CodaWrapper (Coda coda){
        this.coda=coda;
    }

    @Override
    public boolean empty() {
        return coda.empty();
    }

    @Override
    public boolean full() {
        return coda.full();
    }

    @Override
    public int getSize() {
        return coda.getSize();
    }

}
