package Client;

public class Client {
    public static void main(String[]args){

        for (int i=0;i<5;i++){
            System.out.println("Avvio thread "+i);
            ClientWorker thread = new ClientWorker();
            thread.start();
        }

    }    
}
