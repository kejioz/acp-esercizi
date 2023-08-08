package ClientSide;

import java.util.Random;

import ServerSide.Dispatcher.DispatcherProxy;

public class ClientThread extends Thread{
    
    private DispatcherProxy proxy;

    public ClientThread(){
        super();
    }

    @Override 
    public void run(){
        
        for (int i=0;i<3;i++){
            proxy=new DispatcherProxy();
        }
            try{
                sleep(1000);
                Random rand = new Random();
                int cmd= rand.nextInt(4);
                System.out.println("[Thread "+Thread.currentThread().getId()+"] Sending command "+cmd);
                proxy.sendCmd(cmd);
                
            }catch(Exception e){
                e.printStackTrace();
            }
        
    }

}
