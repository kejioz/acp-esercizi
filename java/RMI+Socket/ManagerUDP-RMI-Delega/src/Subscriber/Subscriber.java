package Subscriber;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Service.IManager;


public class Subscriber {
    
    public static void main(String[] args) {
        
        try {
            
            //Cerco registry
            Registry registry = LocateRegistry.getRegistry();
            IManager manager = (IManager) registry.lookup("manager");
            System.out.println("[SUBSCRIBER] TROVATO IL MANAGER ");

            //Creo subscriber passando idComponente e numero porta
            int idComponente = Integer.parseInt(args[0]);
            int porta = Integer.parseInt(args[1]);
            String nomefile = args[2];
            SubscriberSkeleton subscriber = new SubscriberSkeleton(idComponente,porta,nomefile);
            System.out.println("[SUBSCRIBER] MI SOTTOSCRIVO AL MANAGER SU ID "+idComponente+" E VOGLIO ESSERE CONTATTATO SU PORTA "+porta);
            manager.subscribe(idComponente, porta);
            System.out.println("[SUBSCRIBER] SOTTOSCRITTO CON SUCCESSO, AVVIO SKELETON");
            subscriber.runSkeleton();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
