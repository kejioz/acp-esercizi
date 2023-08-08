package threadpipe;

import java.io.*;

public class WriterThread extends Thread {
    
    private DataOutputStream dataOut;

    public WriterThread(PipedOutputStream pipeOut){
        dataOut = new DataOutputStream(pipeOut);
    }

    public void run (){
        //buffered reader server per la lettura dello stream da sysin
        BufferedReader keyboardBuf = new BufferedReader(new InputStreamReader(System.in));
        String s;
        
        //mi metto in un loop infinito
        while (true){
            try{
                System.out.println("        Writer : enter string: ");
                //lettura stringa
                s=keyboardBuf.readLine();
                System.out.println("        Writer: String < "+s+" >output to pipe: ");  
                //output su pipe
                dataOut.writeUTF(s);

            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
