import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public abstract class CounterSkeleton implements ICounter{
    private ServerSocket serverSocket;

    //mi serve solo per gestire le connessioni di skeleton, non implemento; qui devo solo gestire connessione e thread worker
    public CounterSkeleton (){
        try {
            serverSocket = new ServerSocket(5000);          //creato il Serversocket sul localhost e porta 5000
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void runSkeleton(){
        //questo mi tiene in ascolto e attiva i thread?
        try {
            while (true){
                System.out.println("[Server] Attendendo richieste dai client...");
                Socket client = serverSocket.accept(); // quindi qui creo un socket client che si attiva quando arriva una richiesta da un client
                //note : accept è un metodo bloccante, non proseguo fin quando non instanzio una connessione
                //una volta che ho insdtanziato la connessione, creo un thread quando mi arriva un messaggio
                CounterWorker worker = new CounterWorker(client,this); //passo socket e interfaccia
                worker.start(); // starto il thread
            }
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    

}
