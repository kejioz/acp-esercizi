package Server;

public class MagazzinoServer {
    
    public static void main(String[] args) {
        
        MagazzinoSkeleton skeleton = new MagazzinoImpl();
        System.out.println("[Server] Skeleton avviato");
        skeleton.runSkeleton();

    }

}
