package ServerSide.Dispatcher;

import java.net.Socket;
import java.io.*;
import java.net.*;

public class DispatcherProxy implements Dispatcher {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public DispatcherProxy(){
        try{
            socket= new Socket(InetAddress.getLocalHost(),5000);
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    
    public void sendCmd(int cmd){
        try{
            String message = new String ("send#"+cmd+"#");
            out.writeUTF(message);
            out.flush();
            String ack = new String(in.readUTF());
            if (ack.equals("1")){
                System.out.println("[Proxy] Ack");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    
    public int getCmd(){
        int cmd;
        try{
            String message = new String ("get#");
            out.writeUTF(message);
            out.flush();
            String cmdstr = new String(in.readUTF());
            cmd = Integer.parseInt(cmdstr);
        }catch(IOException e){
            cmd=-1;
            e.printStackTrace();
        }
        return cmd;
    }

}
