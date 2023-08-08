package client;

import java.io.IOException;
import java.util.Random;
import java.net.InetAddress;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;



public class Client {
    public static void main(String[] args) {
        //lo faccio partite 5 volte per simulare 5 client
        for (int i=0;i<5;i++){
            Random rand = new Random();
            int val = rand.nextInt(20);

            try {
                Socket s = new Socket(InetAddress.getLocalHost(),5000); // creo socket su localhost e porto 5000
                DataInputStream in = new DataInputStream(s.getInputStream()); 
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                System.out.println("[Client] Scrivo elemento: "+ val);
                out.writeInt(val);
                System.out.println("[Client] Aspetto risposta...");
                int letto = in.readInt();
                System.out.println("[Client] Letto "+ letto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
