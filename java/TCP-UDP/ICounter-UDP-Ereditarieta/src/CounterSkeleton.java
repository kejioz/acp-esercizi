import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public abstract class CounterSkeleton implements ICounter{
    
    public void runSkeleton(){
        try {
            DatagramSocket socket = new DatagramSocket(9000);
            System.out.println("[CounterSkeleton] Entering main loop...");

            while(true){
                byte[] buffer = new byte[100];
                DatagramPacket request = new DatagramPacket(buffer,buffer.length);
                socket.receive(request);

                //avvio worker thread
                CounterWorker worker = new CounterWorker (socket,request,this);
                worker.start();
            }
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
