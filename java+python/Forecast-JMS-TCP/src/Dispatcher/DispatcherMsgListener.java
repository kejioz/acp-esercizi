package Dispatcher;

import javax.jms.*;

public class DispatcherMsgListener implements MessageListener{
    
    private QueueSession qsession;
    private Queue qresponse;
    private int port;

    public DispatcherMsgListener(QueueSession qsession, Queue qresponse, int porto){

        this.qsession=qsession;
        this.qresponse=qresponse;
        this.port=porto;

    }

    @Override
    public void onMessage(Message arg0) {
        
        try{

            //Prendo il messaggio
            TextMessage msg = (TextMessage) arg0;

            String message = msg.getText();

            IDispatcher dispatcher = new DispatcherProxy("localhost",port, qsession, qresponse);

            //Capire se il messaggio è forecast, dovrei vedere anche il metodo
            String[] splitted = message.split("-");
            Integer year_to_predict = Integer.valueOf(splitted[1]);
            dispatcher.forecast(year_to_predict);            

        }catch(JMSException e){
            e.printStackTrace();
        }


    }

}
