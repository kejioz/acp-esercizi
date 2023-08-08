package magazzino;

import coda.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MagazzinoImpl extends UnicastRemoteObject implements Magazzino{
    
    private CodaLock coda;

    public MagazzinoImpl() throws RemoteException {
        coda = new CodaLock(new CodaCircolare(5));
    }

    @Override
    public int preleva() {
        return coda.preleva();
    }

    @Override
    public void deposita(int a) {
        coda.inserisci(a);
    }

}
