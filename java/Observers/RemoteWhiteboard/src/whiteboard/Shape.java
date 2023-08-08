package whiteboard;

import java.io.*;

/*
 * extends Serializable per consentire il trasferimento
 * di oggetti non-remoti Shape come argomenti di una invocazione
 * RMI
 */

public interface Shape extends Serializable {
	
	public void draw ();

}
