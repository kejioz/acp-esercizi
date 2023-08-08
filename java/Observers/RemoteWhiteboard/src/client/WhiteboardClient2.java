package client;

import whiteboard.*;

import java.rmi.registry.*;

public class WhiteboardClient2 {

	public static void main ( String[] args ){
		try{
			
			
			
			/*
			 * 1-Ottiene il riferimento alla lavagna remota.
			 * 2-Crea l'oggetto callback (ObserverImpl)
			 * 3-Invoca il metodo remoto di Witheboard per registrare l'observer 
			 */
			
			
			Registry rmiRegistry = LocateRegistry.getRegistry();
			Whiteboard board = (Whiteboard)rmiRegistry.lookup("myWhiteboard");
			System.out.println ("Got reference < myWhiteboard > " );
			System.out.println ( board.toString() );
			
			Observer observer = new ObserverImpl( board );
			
			System.out.println ("\nObserver with ref: " );
			System.out.println ( observer.toString());
			
			board.attachObserver( observer);
					
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
