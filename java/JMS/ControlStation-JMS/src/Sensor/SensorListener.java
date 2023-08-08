package Sensor;

import javax.jms.*;

import Coda.CodaCircolare;

public class SensorListener implements MessageListener{

    private CodaCircolare coda;

    public SensorListener(CodaCircolare coda){
        this.coda=coda;
    }
    
    @Override
    public void onMessage(Message message) {

        try{

            //Faccio il parsing del messaggio e creo nuovo thread a cui passo la coda
            TextMessage msg = (TextMessage) message;
            String comando = msg.getText();
            System.out.println("[SensorListener] Messaggio "+comando+" ricevuto, creo il thread TManager");
            TManager thread = new TManager(comando, coda);
            thread.start();

        }catch(JMSException e){
            System.err.println("[SensorListener] JMSException");
        }

    }


}
