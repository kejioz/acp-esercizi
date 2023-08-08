package Server;

public class Server {
    public static void main (String[]args){
        DispatcherImpl server = new DispatcherImpl(5);
        server.runSkeleton();
    }
}
