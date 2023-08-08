package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import Service.IPrinter;

//Proxy utilizzato dal Dispatcher per spedire le stringhe alle printers
public class PrinterProxy implements IPrinter{
    
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public PrinterProxy(int porto){
        try{
            socket= new Socket(InetAddress.getLocalHost(),porto);
            System.out.println("\n---INFO SOCKET---\n"+socket.toString()+"\n---INFO SOCKET---\n");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }catch(IOException e){
            System.err.println("[PrinterProxy] Error in IO");
        }
    }

    @Override
    public boolean print(String docName) {
        boolean result=false;
        try {
            //Spedisco il messaggio di print al printer
            System.out.println("[PrinterProxy] Invio "+docName);
            String message = new String("print#"+docName+"#");
            out.writeUTF(message);
            //Aspetto il messaggio di ack per avere la conferma
            System.out.println("[PrinterProxy] Attendo ack");
            result=in.readBoolean();
            System.out.println("[PrinterProxy] Tornato "+result);

        } catch (IOException e) {
            System.err.println("[PrinterProxy] Error in reading message from input stream");
        }

        return result;
    }





}
