package Server;

import Service.*;

import java.rmi.RemoteException;

import java.util.Vector;

//Ricorda che siccome non hai usato extend UnicastRemoteObject lo devi esportare nel server
public class DispatcherImpl implements IDispatcher {
    
    private static final long serialVersionUID=10L;

    private Vector<PrinterSkeletonDelega> registeredPrinters;

    //costruttore
    protected DispatcherImpl() throws RemoteException{
        registeredPrinters = new Vector<PrinterSkeletonDelega>();
        //nota qui avrei anche potuto chiamare semplicemente super() e avrei potuto evitare di fare l'export nel server se ho capito bene
    }
    
    //metodi
    @Override
    public void addPrinter(PrinterSkeletonDelega printer) throws RemoteException {
        registeredPrinters.add(printer);
        System.out.println("[DispatcherImpl] Printer aggiunta!");       
        //implementare callback distribuita con notify quindi immagino, ma ricorda basata su socket
    }
    
    @Override
    public boolean printRequest(String docName) throws RemoteException {
        
        System.out.println("[DispatcherImpl] Ricevuta richiesta di print");

        for (int i=0;i<registeredPrinters.size();i++){
            PrinterProxy proxy = new PrinterProxy(registeredPrinters.get(i).getPorto()); // creo la proxy sullo stesso porto di quello skeleton
            boolean risultato = proxy.print(docName);
            System.out.println("[DispatcherImpl] Richiesta di print correttamente inoltrata!");
            if (risultato){
                return true;
            }
        }

        return false;

    }

  



}
