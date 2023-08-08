package Server;


public class ServerPrinter{
    
    public static void main(String[] args) {
        
        PrinterSkeletonDelega printer = new PrinterSkeletonDelega(Integer.parseInt(args[0]));
        printer.runSkeleton();
    }

}

