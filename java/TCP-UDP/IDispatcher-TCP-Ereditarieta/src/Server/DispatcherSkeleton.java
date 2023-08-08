package Server;

import Service.Dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class DispatcherSkeleton implements Dispatcher {
    
    private ServerSocket socket;

    public DispatcherSkeleton(){
        try{
            socket=new ServerSocket(5000);
        }catch(IOException e){
            System.err.println("Error in initializing serversocket");
        }
    }

    public void runSkeleton(){
        while(true){
            try{
                System.out.println("[Server] In ascolto sulla porta 5000..");
                Socket client=socket.accept(); //facendo socket.accept sto ritornando al client la socket instanziata con skel
                System.out.println("[Server] Connessione accettata con "+client.getInetAddress());
                DispatcherWorker worker = new DispatcherWorker(this,client);
                worker.start();
            }catch(IOException e){
                System.err.println("[Server] Couldn't accept client");
            }
        }
    }

}
