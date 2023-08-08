package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Coda.*;

public class ServerImpl extends UnicastRemoteObject implements IServer{
 
    private Coda coda;

    public ServerImpl() throws RemoteException{
        coda = new CodaLock(new CodaCircolare(5));
    }

    @Override
    public void deposita(int p) throws RemoteException {
        coda.inserisci(p);
        System.out.println("[Server] Depositato il prodotto dal codice "+p);
    }

    @Override
    public int preleva() throws RemoteException {
        int x=coda.preleva();
        System.out.println("[Server] Ho prelevato il prodotto "+x);
        return x;
    }

}
