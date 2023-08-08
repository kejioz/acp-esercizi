package ServerSide.Dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class DispatcherSkeleton implements Dispatcher{
    //Skeleton per gestire connessioni e avviare thread nel runskeleton
    private ServerSocket serverSocket;

    public DispatcherSkeleton(){
        try{
            serverSocket= new ServerSocket(5000);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void runSkeleton(){
        try{
            while(true){
                System.out.println("[Skeleton] Server in attesa..");
                Socket client = serverSocket.accept();
                System.out.println("[Skeleton] Connessione accettata..");
                DispatcherWorker worker = new DispatcherWorker(client, this);   //passo ai worker la socket client e lo skeleton
                worker.start(); //starto i worker                
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
