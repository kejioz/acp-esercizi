package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Worker extends Thread {
    private Socket socket; //gli passo la socket
    DataInputStream in;
    DataOutputStream out;

    public Worker (Socket s){
        socket = s;
    }

    @Override
    public void run(){
        try {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                int val = in.readInt(); //leggo il mess in input
            System.out.println("[WorkerThread] Thread avviato, ho ricevuto l'elemento "+val+"; lo modifico");
                val = val+2;
            System.out.println("[WorkerThread] Valore modificato a "+val);
                out.writeInt(val);
                out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}