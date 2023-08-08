package IService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoggerSkeleton implements ILogger{
    
    private LoggerImpl logger;
    private ServerSocket serverSocket;
    private int porto;

    public LoggerSkeleton(int porto){
        this.porto=porto;
        logger = new LoggerImpl();
        try {
            serverSocket = new ServerSocket(porto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runSkeleton(){

        System.out.println("[SKELETON] In ascolto sul porto "+porto);

        while (true){

            try {

                //Accetto connessione con il client
                Socket client = serverSocket.accept();
                System.out.println("[SKELETON] Connessione accettata, avvio il worker..");
                //Avvio il Thread Worker
                LoggerWorker worker = new LoggerWorker(this, client);
                worker.start();

            
            } catch (IOException e) {
                
                e.printStackTrace();
            }

        }

    }
    
    @Override
    public void registraDato(int dato) {
        this.logger.registraDato(dato);
    }
    


}
