package Client;

import Service.Dispatcher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DispatcherProxy implements Dispatcher {
    
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public DispatcherProxy(){
        try{
            socket = new Socket(InetAddress.getLocalHost(),5000);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }catch(UnknownHostException eh){
            System.err.println("Unknown host!");
        }catch(IOException e){
            System.err.println("Error in streams");
        }
    }

    @Override
    public void sendCmd(int c){
        try{
            //Mando il messaggio contentente la richiesta di send ed il comando
            System.out.println("[Proxy] Sending command "+c);
            String cmdstr = new String(Integer.toString(c));
            String msg=new String("send#"+cmdstr+"#");
            out.writeUTF(msg);
            //Prendo ack
            String response = new String(in.readUTF());
            if (response.compareTo("1")==0){
                System.out.println("[Proxy] Acknowledged! Command "+c+" correctly sent");
            }
            else{
                System.out.println("[Proxy] No ack...");
            }
        }catch(IOException e){
            System.err.println("[Proxy] IOException detected");
        }
    }

    @Override
    public int getCmd(){
        Integer x=-1;
        try{
            //Mando il messaggio contenente la richiesta di get
            System.out.println("[Proxy] Getting command..");
            out.writeUTF("get#");
            //Adesso prendo il comando che poi viene dato all'attuatore
            x=in.readInt();
            System.out.println("[Proxy] Got command "+x);
            return x;
        }catch(IOException e){
            System.err.println("[Proxy] IOException detected");
        }
        return x;
    }
}
