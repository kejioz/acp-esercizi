import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class CounterWorker extends Thread{
    //fa il parsing input output e fa upcalling?

    private Socket client;
    private ICounter skeleton;
    DataInputStream in;
    DataOutputStream out;

    public CounterWorker(Socket cl, ICounter sk){
        try {
            client = cl;
            skeleton = sk;
            in= new DataInputStream(new BufferedInputStream(cl.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(cl.getOutputStream()));
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        try {
            String message = new String (in.readUTF());
            //una volta letto utilizzo i metodi di tokenizer
            StringTokenizer tokens = new StringTokenizer (message,"#"); //utilizzo # per il parsing
            String method = new String (tokens.nextToken()); //attraverso il parsing capisco il metodo

            //in base al metodo opero
            if (method.compareTo("set")==0){
                int s = Integer.valueOf(tokens.nextToken()).intValue(); //prendo la s grazie a next token
                //faccio la upcall allo skeleton
                skeleton.setCount(message,s);
                System.out.println(("[CounterWorker] Impostato il counter"));

                String res = new String ("1"); //acknowledge
                out.writeUTF(res);
                out.flush();
            }
            else if (method.compareTo("sum")==0){
                int s =Integer.valueOf(tokens.nextToken()).intValue(); //prendo la s grazie a next token
                //faccio la upcall allo skeleton
                int x=skeleton.sum(s);
                System.out.println(("[CounterWorker] Sommato "+s+" al counter"));
                //ora flusho il contatore
                out.writeInt(x);
                out.flush();
            }
            else if (method.compareTo("increment")==0){
                int x=skeleton.increment();
                System.out.println(("[CounterWorker] Sommato 1 al counter"));
                out.writeInt(x);
                out.flush();
            }else{
                System.out.println("Error");
            }

        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        
    }
}
