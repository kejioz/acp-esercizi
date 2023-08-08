package ServerSide.Dispatcher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class DispatcherWorker  extends Thread {
    private Socket client;
    private Dispatcher skeleton;
    DataInputStream in;
    DataOutputStream out;

    public DispatcherWorker(Socket cl,Dispatcher sk){
        try{
            client = cl;
            skeleton = sk;
            in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try{
            String message = new String(in.readUTF());
            StringTokenizer token = new StringTokenizer(message,"#");
            String method = token.nextToken();

            //send command
            if (method.compareTo("send")==0){
                int cmd = Integer.parseInt(token.nextToken());
                skeleton.sendCmd(cmd);
                String res = new String("1");
                out.writeUTF(res);
                out.flush();
                System.out.println("[Worker] Ho aggiunto alla coda "+cmd);
            }
            //get command
            else if (method.compareTo("get")==0){
                int c = skeleton.getCmd();
                String res = new String(Integer.toString(c));
                out.writeUTF(res);
                out.flush();
                System.out.println("[Worker] Ho mandato all'attuatore "+c);
            }
            else{
                System.out.println("Error");
            }
        }catch(IOException e){
            System.err.println("Errore nel prendere gli stream");
        }
    }

}
