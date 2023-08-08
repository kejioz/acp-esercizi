package Client;

import Whiteboard.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.Random;

public class ClientPopulate {
    
    public static void main(String[] args) {
        
        try{
            //Prendo il riferimento alla lavagna
            Registry rmiRegistry = LocateRegistry.getRegistry();
            Whiteboard whiteboard = (Whiteboard)rmiRegistry.lookup("whiteboard");
            System.out.println("[ClientPopulate]Ottenuto riferimento alla lavagna");

            //Aggiungo Shapes alla lavagna randomly, la popolo chiamando i metodi
            Shape shape;
            int x;
            Random rand=new Random();

            for (int i=0;i<4;i++){

                x=rand.nextInt(11);

                if (x<=5){
                    shape=new Triangle();
                }else{
                    shape=new Square();
                }

                System.out.println("[ClientPopulate]Adding shape "+shape.toString());

                //Aggiungo la Shape chiamando il metodo remoto della lavagna

                whiteboard.addShape(shape);
                Thread.sleep(6000);

            }
        }catch(NotBoundException e){
            System.err.println("[ClientPopulate]Not bound exc");
        }catch(RemoteException e){
            System.err.println("[ClientPopulate]Remote exception");
        }catch(InterruptedException e){
            System.err.println("[ClientPopulate]Interrupted exception");
        }

    }

}
