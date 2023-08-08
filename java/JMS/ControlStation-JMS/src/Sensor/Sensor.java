package Sensor;

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.*;

import Coda.CodaCircolare;


public class Sensor {

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
            TopicConnection tconn = tconnf.createTopicConnection(); //Sono sub quindi devo startarla
            tconn.start();

            //Creo session
            TopicSession tsess = tconn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            //Creo subscriber
            TopicSubscriber tsub = tsess.createSubscriber(topic);

            //Setto il listener e gli passo la coda di dimensione D
            int D = Integer.parseInt(args[0]);
            CodaCircolare coda = new CodaCircolare(D);
            
                //Creo il TExecutor a cui passo la coda
                TExecutor exec= new TExecutor(coda);
                exec.start();

            SensorListener listener = new SensorListener(coda);
            tsub.setMessageListener(listener);
            System.out.println("[Sensor] Listener settato!");



        } catch (NamingException e) {
            System.err.println("[ControlStation] Naming Exception");
        } catch (JMSException e){
            System.err.println("[ControlStation] JMSException");
        } catch (Exception e){
            System.err.println("[ControlStation] InterruptedException");
        }


    }

}
