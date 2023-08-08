package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.Vector;

import Whiteboard.*;

public class WhiteboardImpl extends UnicastRemoteObject implements Whiteboard {

    private int count;
    private Vector<Shape> whiteboardContent; //contiene il vettore di shapes
    private Vector<Observer> attachedObservers; //contiene il vettore di observers attached

    protected final static long serialVersionUID=10L;

    //Costruttore di WhiteboardImpl
    public WhiteboardImpl()throws RemoteException{
        count = 0;
        whiteboardContent = new Vector<Shape>();
        attachedObservers = new Vector<Observer>();
    }

    //Inherited abstract methods implementation
    @Override
    public void addShape(Shape s) throws RemoteException{
        ++count;
        System.out.println("[Whiteboard]Adding the Shape "+count+" "+s.toString());
        s.draw();
        whiteboardContent.add(s);
        //Since i updated the board content, i notify all the Observers
        notifyAllObservers();
    }

    @Override
    public Vector<Shape> getShapes() throws RemoteException{
        return whiteboardContent;
    }
    
    @Override
    public void attachObserver(Observer observer) throws RemoteException{

        System.out.println("[Whiteboard]New observer attached!");
        attachedObservers.add(observer);

    }

    //Metodo private per notificare gli observers
    //Vado ad invocare la Notify degli observers che espongono notify
    private void notifyAllObservers(){

        System.out.println("[Whiteboard]Updated! Notifying all the observers..");
        
        for (int i=0;i<attachedObservers.size();i++){
            try{
                attachedObservers.get(i).observerNotify();
            }catch(RemoteException e){
                System.err.println("[Whiteboard] Notify error!");
            }
        }
    }

}

