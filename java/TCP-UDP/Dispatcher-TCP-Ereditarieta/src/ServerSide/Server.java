package ServerSide;

import ServerSide.Dispatcher.Dispatcher;
import ServerSide.Dispatcher.DispatcherImpl;
import ServerSide.Dispatcher.DispatcherSkeleton;

public class Server {
    
    public static void main (String[] args){
        DispatcherImpl skeleton = new DispatcherImpl();
        skeleton.runSkeleton();
    }

}
