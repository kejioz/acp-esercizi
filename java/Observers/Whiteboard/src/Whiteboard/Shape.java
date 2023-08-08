package Whiteboard;

import java.io.Serializable;

public interface Shape extends Serializable {

    public void draw();

}

//pongo l'interfaccia Serializzabile siccome devo 
//serializzare gli oggetti di questa classe, le shapes