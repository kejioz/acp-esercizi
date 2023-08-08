package ClientSide;

import java.util.Timer;
import java.util.TimerTask;

import ServerSide.Dispatcher.DispatcherProxy;

public class Client {
    public static void main (String[] args){
        for (int i=0;i<5;i++){
            System.out.println("Avvio nuovo thread.,,");
            ClientThread client = new ClientThread();
            client.run();
            System.out.println("[Client] Il thread "+i+" ha eseguito correttamente");
        }
    }
}