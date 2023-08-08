package Server;

import Service.ILogger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;

public class LoggerThread extends Thread {

    private DatagramSocket socket;
    private ILogger skeleton;
    private DatagramPacket message;
    
    public LoggerThread(DatagramSocket socket,ILogger skeleton,DatagramPacket message){
        this.socket=socket;
        this.skeleton=skeleton;
        this.message=message;
        System.out.println("[Logger"+Thread.currentThread()+"] Inizializzato");
    }

    @Override
    public void run() {
        
        try{
            
            //Estraggo l'int dal pacchetto
            Integer dato= Integer.valueOf(new String(message.getData(),0,message.getLength()));
            System.out.println("[Logger"+Thread.currentThread()+"] Ho estratto il dato " +dato);

            //Effettuo l'upcall del metodo al LoggerImpl
            skeleton.registraDato(dato);
            System.out.println("[Logger"+Thread.currentThread()+"] Upcall effettuata con successo");

            //Messaggio di ack
            String response = new String ("ack");
            DatagramPacket risposta = new DatagramPacket(response.getBytes(),response.length(),InetAddress.getLocalHost(),message.getPort());
            socket.send(risposta);

        }catch(UnknownHostException e){
            System.out.println("[Logger"+Thread.currentThread()+"] Host sconosciuto");
        }catch(IOException e){
            System.out.println("[Logger"+Thread.currentThread()+"] IOException");
        }

    }

}
