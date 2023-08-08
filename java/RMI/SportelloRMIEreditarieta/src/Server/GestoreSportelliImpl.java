package Server;

import java.rmi.RemoteException;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.rmi.server.UnicastRemoteObject;

import Service.GestoreSportelli;
import Service.Sportello;

public class GestoreSportelliImpl extends UnicastRemoteObject implements GestoreSportelli{

    private static final long serialVersionUID = 15L;
    
    private Vector<Sportello> sportelli;

    protected GestoreSportelliImpl() throws RemoteException{
        sportelli = new Vector<Sportello>();
    }

    @Override
    public boolean sottoponiRichiesta(int idcliente)throws RemoteException{
        
        boolean result = false;
        int i=0;

        while ((!result)&&(i<sportelli.size())){
            result = sportelli.get(i).serviRichiesta(idcliente);
            i++;
        }

        System.out.println("[Gestore] La richiesta di "+idcliente+" è terminata con esito "+((result==true)?"positivo":"negativo"));

        return result;
    
    }

    public void sottoscrivi(Sportello s)throws RemoteException{
        sportelli.add(s);
        System.out.println("[GestoreSportelli] Ricevuta richiesta di sottoscrizione di un nuovo sportello");
    }

}
