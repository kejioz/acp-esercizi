package ControlStation;

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.*;

public class ControlStation {
    
    public static void main(String[] args) {
        
        //Creo hash
        Hashtable<String,String> prop = new Hashtable<String,String>();

        //INserisco proprietà per provider e per activemqinitialcontet
        prop.put("java.naming.provider.url","tcp://127.0.0.1:61616");
        prop.put("java.naming.factory.initial","org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        prop.put("topic.Topic","Topic");

        //Contesto
        try {
            //passo la hashtable al mio initialcontext
            Context ctx = new InitialContext(prop);

            //Prendo i miei administered object, qconnfactory e destination
            TopicConnectionFactory tconnf = (TopicConnectionFactory) ctx.lookup("TopicConnectionFactory");
            Topic topic = (Topic) ctx.lookup("Topic");

            //Creo connection
            TopicConnection tconn = tconnf.createTopicConnection(); //Sono publisher quindi non devo startarla

            //Creo session
            TopicSession tsess = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creo il publisher
            TopicPublisher tpub = tsess.createPublisher(topic);
            System.out.println("[ControlStation] Publisher creato!");
            //Creo il message
            TextMessage message = tsess.createTextMessage();

            //Mando N messaggi ogni secondo che possono essere startSensor, stopSensor e read
            int N = Integer.parseInt(args[0]);

            for (int i=0;i<N;i++){

                double rand = Math.random();
                if (rand<=0.33){
                    message.setText("startSensor");
                }else if (rand>=0.34 && rand<=0.66){
                    message.setText("stopSensor");
                }else{
                    message.setText("read");
                }
                
                tpub.publish(topic,message);
                System.out.println("[ControlStation] Messaggio "+message.getText()+" pubblicato");
                Thread.sleep(1000);

            }

            tpub.close();
            tsess.close();
            tconn.close();

        } catch (NamingException e) {
            System.err.println("[ControlStation] Naming Exception "+e.getMessage());
        } catch (JMSException e){
            System.err.println("[ControlStation] JMSException");
        } catch (InterruptedException e){
            System.err.println("[ControlStation] InterruptedException");
        }
    


    }

}
