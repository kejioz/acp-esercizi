package IService;

public class Logger {
    
    public static void main(String[] args) {
        
        int porta =Integer.parseInt(args[0]);
        LoggerSkeleton skeleton = new LoggerSkeleton(porta);
        System.out.println("[LOGGER] Avviato, runno lo skeleton.");
        skeleton.runSkeleton();

    }

}
