package Server;

public class LoggerServer {
    
    public static void main(String[] args) {
        
        LoggerImpl skeleton = new LoggerImpl();
        skeleton.runSkeleton(Integer.valueOf(args[0]));
        
    }

}
