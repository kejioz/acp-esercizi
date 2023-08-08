package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import Interface.IMagazzino;    

public abstract class MagazzinoSkeleton implements IMagazzino {

    private DatagramSocket socket;
    private DatagramPacket packet;

    public void runSkeleton(){

        try{
            socket = new DatagramSocket(3000);
            System.out.println("[Skeleton] Running on address "+socket.getLocalPort());
            while(true){
                byte[] buffer = new byte[1024];
                packet = new DatagramPacket(buffer,buffer.length);
                socket.receive(packet);
                System.out.println("[Skeleton] Pacchetto ricevuto");
                MagazzinoThread thread = new MagazzinoThread(this,socket,packet);
                thread.start();

            }
        }catch (SocketException e){
            System.err.println("[Skeleton] SocketExc "+e.getMessage() );
        }catch (UnknownHostException e){
            System.err.println("[Skeleton] Unknown host "+e.getMessage() );
        }catch (IOException e){
            System.err.println("[Skeleton] IOException");
        }

    }

}
