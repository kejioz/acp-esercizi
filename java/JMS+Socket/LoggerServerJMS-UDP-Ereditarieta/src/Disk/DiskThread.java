package Disk;

import javax.jms.JMSException;
import javax.jms.MapMessage;

import org.apache.activemq.command.Message;

public class DiskThread extends Thread {
    
    private MapMessage mapmessage;

    public DiskThread(MapMessage mapmessage){
        this.mapmessage=mapmessage;
        System.out.println("[Disk"+Thread.currentThread()+"] Inizializzato");
    }

    @Override
    public void run() {
            
        try{
            System.out.println("[Disk"+Thread.currentThread()+"] Avvio il proxy ....");
            int dato = mapmessage.getInt("dato");
            int porto = mapmessage.getInt("porto");
            LoggerProxy proxy = new LoggerProxy(porto);
            System.out.println("[Disk"+Thread.currentThread()+"] Avviato il proxy, gli passo il porto"+porto+" con il dato "+dato);
            proxy.registraDato(dato);


        }catch(JMSException e){
            System.err.println("[Disk"+Thread.currentThread()+"] Errore JMS");
        }

    }

}
