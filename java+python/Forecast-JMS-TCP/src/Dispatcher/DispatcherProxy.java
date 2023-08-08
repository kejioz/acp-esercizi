package Dispatcher;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.jms.*;

public class DispatcherProxy implements IDispatcher {

    private String addr;
    private int port;
    private QueueSession qsession;
    private Queue qresponse;
    
    public DispatcherProxy (String a, int p, QueueSession qsession, Queue resp){

        this.addr = new String (a);
        this.port = p;
        this.qsession=qsession;
        this.qresponse=resp;

    }

    public void forecast(int year){

        try{

            //Creo socket
            Socket s = new Socket(addr,port);

            //Streaming
            DataOutputStream dataOut = new DataOutputStream (s.getOutputStream());
            BufferedReader dataIn = new BufferedReader(new InputStreamReader(s.getInputStream()));

            dataOut.writeUTF("forecast-"+year);

            //Leggo risultato
            String result = dataIn.readLine();

            //Creo messaggio e lo sendo
            TextMessage message = qsession.createTextMessage("forecast-"+result);

            QueueSender sender = qsession.createSender(qresponse);

            sender.send(message);


            //Chiudo la socket;
            s.close();

        }catch(Exception e){
            e.printStackTrace();
        }


    }

}
