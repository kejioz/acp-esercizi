package Client;

import java.util.Random;

public class ClientAThread extends Thread{
    
    protected ClientAThread(){
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
                int id = rand.nextInt(50);

                if (Math.random()>0.5){
                    articolo="smartphone";

                }else{
                    articolo="laptop";
                }

                proxy.deposita(articolo, id);

            } catch (InterruptedException e) {
                System.err.println("[ClientAThread] Interrupted");
            }
            
        }

    }

}
