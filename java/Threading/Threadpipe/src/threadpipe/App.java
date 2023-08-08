package threadpipe;

import java.io.PipedOutputStream;

public class App {
	
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		
		PipedOutputStream pipeOut = new PipedOutputStream();
		
		WriterThread w = new WriterThread (pipeOut);
		ReaderThread r = new ReaderThread (pipeOut);
		
		w.start();
		r.start();
		
		
	}

}
