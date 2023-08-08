
public class Worker extends Thread{

    private Coda wrapper;
    private boolean flag;

    public Worker(Coda w, boolean f){
        wrapper=w;
        flag=f;
    }


    @Override
    public void run() {
        
        if(flag){
            int x = (int)Math.random() + (5*2);
            wrapper.inserisci(x);
        } else {
            int x = wrapper.preleva();
            System.out.println("[Worker] Consumato: "+x);
        }
    }
    
}