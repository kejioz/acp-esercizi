package Client;

import java.util.Random;

public class ClientWorker extends Thread{
    
    private DispatcherProxy proxy;

    public ClientWorker(){
        super();
    }

    @Override
    public void run(){  //dentro la run ogni volta devo chiamare la send

        for (int i=0;i<3;i++){
            proxy = new DispatcherProxy(); // ogni volta devo rifare la socket siccome TCP
            
            try{
                sleep(2000);
                Random rand = new Random();
                int x = rand.nextInt(4);    //next int gets the next random from seed included from 0 and the N number in this case 4
                System.out.println("[ClientWorker] Ho inviato "+x);
                proxy.sendCmd(x);

            }catch(InterruptedException e){
                System.err.println("[ClientWorker] Exception!");
            }
        }

    }

}
