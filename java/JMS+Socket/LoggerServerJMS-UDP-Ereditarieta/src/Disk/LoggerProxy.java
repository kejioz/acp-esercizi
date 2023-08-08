package Disk;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import Service.ILogger;

public class LoggerProxy implements ILogger{
    
    private int dato;
    private int porto;

    public LoggerProxy (int porto){
        this.porto=porto;
    }

    @Override
    public void registraDato(int dato) {
        
        try{
            this.dato=dato;
            System.out.println("[LoggerProxy] Creo il proxy sul porto "+porto);
            //Creo la socket
            DatagramSocket socket = new DatagramSocket();

            //Creo il pacchetto col dato
            String message = new String(Integer.toString(dato));
            DatagramPacket packet = new DatagramPacket(message.getBytes(),message.length(),InetAddress.getLocalHost(),porto);
            
            //Lo sendo
            socket.send(packet);
            System.out.println("[LoggerProxy] Pacchetto con dato "+dato+" mandato correttamente al porto "+porto);

            //Ricevo l'ack
            byte[] buf = new byte[10];
            DatagramPacket ack = new DatagramPacket(buf,buf.length);
            socket.receive(ack);
            String ackstring = new String (ack.getData(),0,ack.getLength());
            System.out.println("[LoggerProxy]"+ackstring);

        }catch(SocketException e){
            System.err.println("[LoggerProxy] SocketException "+ e.getMessage());
        }catch(UnknownHostException e){
            System.err.println("[LoggerProxy] Host sconosciuto");
        }catch(IOException e){
            System.err.println("[LoggerProxy] IOException");
        }
    }
    

}
