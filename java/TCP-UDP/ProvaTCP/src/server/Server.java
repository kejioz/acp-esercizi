package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ServerSocket server;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //in ascolto per accept
        while (true){
            try {
                System.out.println("[Server] In attesa di richieste sulla porta 5000...");
                Socket client = server.accept(); //quando ho una richiesta dal client instanzio la connessione e creo un worker
                System.out.println("[Server] Connessione instaurata, inizializzazione thread");
                Worker worker = new Worker(client); //creo il thread a cui passo il socket client; lui si occuperà della comunicazione
                worker.start(); //starto il thread

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
