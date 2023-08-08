package Client;

public class Actuator {
    
    public static void main(String[] args){

        while(true){    //devo farlo all'infinito

            DispatcherProxy proxy=new DispatcherProxy();
            Integer x =proxy.getCmd();
            System.out.println("[Actuator] Prelevato il comando "+x);

            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.err.println("Errore interruzione");
            }

        }
    }

}
