package Client;

public class ClientA {

    public static void main(String[] args) {
        
        ClientAThread[] threads = new ClientAThread[5];

        for (int i=0;i<5;i++){
            
            threads[i] = new ClientAThread();
            threads[i].start();

        }
        
    

    }

}