package ServerSide.Dispatcher;

import ServerSide.Coda.*;

public class DispatcherImpl extends DispatcherSkeleton {
    private Coda coda;

    public DispatcherImpl(){
        super();
        coda = new CodaCircolare(5);
    }

    @Override
    public void sendCmd(int command){
        coda.inserisci(command);
    }

    @Override
    public int getCmd(){
        int x=coda.preleva();
        return x;
    }

}
