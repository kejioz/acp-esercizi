package Client;

import java.util.Random;

public class ClientBThread extends Thread{
    
    protected ClientBThread(){
        super();
    }

    @Override
    public void run() {
        
        Random rand = new Random();

        MagazzinoProxy proxy = new MagazzinoProxy();

        for (int i=0;i<3;i++){

            try {

                Thread.sleep((rand.nextInt(3)+2)*1000);

                //Creo articolo e id a caso
                String articolo="";

                if (Math.random()>0.5){
                    articolo="smartphone";

                }else{
                    articolo="laptop";
                }

                int id=proxy.preleva(articolo);

                System.out.println("[ClientBThread] Prelevato "+articolo+" dall'id "+id);

            } catch (InterruptedException e) {
                System.err.println("[ClientBThread] Interrupted");
            }
            
        }

    }

}
