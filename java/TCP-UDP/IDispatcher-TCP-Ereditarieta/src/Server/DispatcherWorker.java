package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

import Service.Dispatcher;

public class DispatcherWorker extends Thread{

    private Dispatcher skeleton;
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;

    public DispatcherWorker(Dispatcher sk,Socket cl){
        skeleton=sk;
        client=cl;
        try{
            in= new DataInputStream(client.getInputStream());
            out= new DataOutputStream(client.getOutputStream());
        }catch(IOException e){
            System.err.println("[DispatcherWorker] Error in taking streams");
        }
    }

    @Override
    public void run(){
        try{
            String message = new String(in.readUTF());
            StringTokenizer tokens = new StringTokenizer(message,"#");
            String method = new String (tokens.nextToken());

            if (method.compareTo("send")==0){
                //if the method is send then i have to explore the token and send the command
                System.out.println("[DispatcherWorker] Received a send request");
                String stringcmd = tokens.nextToken();
                Integer cmd = Integer.valueOf(stringcmd);
                skeleton.sendCmd(cmd);
                System.out.println("[DispatcherWorker] Sending the command "+cmd+" to the Dispatcher");
                //now i have to send the ack message
                out.writeUTF("1");
            }
            else if (method.compareTo("get")==0){
                //if the method is get i simply have to return the command
                System.out.println("[DispatcherWorker] Received a get request");
                Integer cmd = skeleton.getCmd();
                System.out.println("[DispatcherWorker] Sending the command "+cmd+" to the Actuator");
                out.writeInt(cmd);
            }
            else{
                System.err.println("Bad method");
            }
        }catch(IOException e){
            System.err.println("Error in reading message");
        }
    }
}