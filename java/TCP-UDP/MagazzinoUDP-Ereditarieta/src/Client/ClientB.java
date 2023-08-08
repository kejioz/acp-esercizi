package Client;

public class ClientB {
    
    public static void main(String[] args) {
        
        ClientBThread[] threads = new ClientBThread[5];

        for (int i=0;i<5;i++){
            
            threads[i] = new ClientBThread();
            threads[i].start();

        }

    }

}
