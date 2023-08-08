package ClientSide;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Timer;

import ServerSide.Dispatcher.DispatcherProxy;

public class Actuator {
    
    public static void main (String[] args){
    
        while(true){
            DispatcherProxy dispatcher = new DispatcherProxy();
            Integer c=dispatcher.getCmd();
            System.out.println("Il comando è "+c);

            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.err.println("Errore interruzione!");
            }
        }
    }

}
