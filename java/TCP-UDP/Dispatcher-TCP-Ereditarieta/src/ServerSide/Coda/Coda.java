package ServerSide.Coda;

public interface Coda {

    public void inserisci(int s);
    public int preleva();


    public boolean empty();
    public boolean full();

    public int getSize();
}
