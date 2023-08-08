package threadpipe;

import java.io.*;

//readerThread will read our content from the pipe

public class ReaderThread extends Thread {
    private DataInputStream dataIn;

    public ReaderThread(PipedOutputStream pipeOut ){
        try {
            
            dataIn= new DataInputStream(new PipedInputStream(pipeOut));
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void run(){
        String s;
        //mi metto in un loop infinito
        while (true){
            try {
                System.out.println("        READER: waiting for new input..");
                //ora ho input dal pipe
                s=dataIn.readUTF();
                System.out.println("        READER: input from pipe: "+s);
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
    
}
