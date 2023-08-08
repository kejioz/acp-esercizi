package Client;

import Whiteboard.Shape;

public class Square implements Shape{

    private static final long serialVersionUID=2L;

    public void draw(){
        System.out.println ("+--+");
		System.out.println ("|  |");
		System.out.println ("+--+");
    }
    
}
