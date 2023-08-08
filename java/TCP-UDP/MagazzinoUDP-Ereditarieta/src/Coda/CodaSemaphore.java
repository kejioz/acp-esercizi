package Coda;

import java.util.concurrent.Semaphore;


public class CodaSemaphore extends CodaWrapper{
    
    private Semaphore disp_articolo;
    private Semaphore disp_spazio;

    public CodaSemaphore(Coda c){
        super(c);
        disp_spazio=new Semaphore(coda.getSize());
        disp_articolo=new Semaphore(0);
    }

    @Override
    public int preleva() {

        int articolo=0;
        
        try{

            disp_articolo.acquire();

            try{

                synchronized (coda){
                    articolo = coda.preleva();
                }

            }finally{
                disp_spazio.release();
            }

        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("[Coda] Articolo id "+articolo+" prelevato");
        return articolo;

    }

    @Override
    public void inserisci(int articolo) {
        
        try{

            disp_spazio.acquire();

            try{

                synchronized (coda){
                    coda.inserisci(articolo);
                }

            }finally{
                disp_articolo.release();
            }

        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("[Coda] Articolo id "+articolo+" depositato");

    }

}
