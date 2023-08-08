package Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import Service.ILogger;

public abstract class LoggerSkeleton implements ILogger {
    
    protected void runSkeleton(int port){

        try{

            DatagramSocket socket = new DatagramSocket(port);

            while(true){

                System.out.println("[LoggerSkeleton] Server in ascolto...");
                byte[] buffer = new byte[10];
                DatagramPacket message = new DatagramPacket(buffer,buffer.length);
                socket.receive(message);
                LoggerThread thread = new LoggerThread(socket,this,message);
                thread.start();

            }

        }catch(Exception e){

        }

    }

}
