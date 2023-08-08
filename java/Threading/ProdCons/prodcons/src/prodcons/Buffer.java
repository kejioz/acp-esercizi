package prodcons;

public class Buffer {
    private long content;
    private boolean full;

    public Buffer() {
        content=0;
        //condizione iniziale falsa
        //FALSE = contenuto del buffer non prodotto
        //TRUE = contenuto del buffer prodotto
        full=false;
    }

    //produzione: se full è false, quindi non è stato prodotto, allora produce il content e lo printa
    public synchronized void produci () {
        //printa quale thread sta invocando il produci
        System.out.println(Thread.currentThread().getName()+" invocazione produci");
        //se full è vero, quindi c'è contenuto, metti in wait
        while (full){
            System.out.println(Thread.currentThread().getName()+" in attesa (Buffer pieno)");
            try{
                wait();
                            
            } catch (InterruptedException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        content=System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName()+" : prodotto = "+content);
        full=true; //adesso è full quindi non posso piu produrre
        
        notifyAll();
    }

    //consumazione: se full è true, quindi c'è il contenuto, lo consuma e manda una notify all per sbloccare i thread in wait
    public synchronized void consuma () {
        //printa quale thread stsa invocando il consuma
        System.out.println(Thread.currentThread().getName()+" invocazione consuma");
        //se full è false, quindi no content
        while(!full){
            System.out.println(Thread.currentThread().getName()+" in attesa (Buffer vuoto)");
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+" : consumato= "+content);
        full=false;
        notifyAll();
    }
}
