package Dispatcher;

import java.util.ArrayList;
import java.util.Random;

import Reading.Reading;
import Service.IDispatcher;
import Service.Observer;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class DispatcherImpl extends UnicastRemoteObject implements IDispatcher{

    private ArrayList <Observer> observersTemp;
    private ArrayList <Observer> observersPress;
    private Reading stato;
    
    public DispatcherImpl() throws RemoteException {
        observersTemp = new ArrayList<Observer>();
        observersPress = new ArrayList<Observer>();
    }

    @Override
    public synchronized void setReading(Reading reading) {

        try{

            //Deve durare un tempo a caso tra 1 e 5 secondi
            Random rand = new Random();
            Thread.sleep((rand.nextInt(5)+1)*1000);

            //Che tipo è il reading passato?
            String tipo = reading.getTipo();
            stato = reading;

            //Se gli array hanno elementi li notifico
            if (tipo.compareTo("temperatura")==0){

                //Caso observersTemp
                if (observersTemp.size()!=0){
                    //Se ci sono observers li notifico
                    for (int i=0;i<observersTemp.size();i++){
                        try{
                            (observersTemp.get(i)).notifyReading();
                            System.out.println("[Dispatcher] Observer temperatura notified! Current state is "+reading.getValore());
                        }catch(RemoteException e){
                            System.err.println("[Dispatcher] "+e.getMessage());
                        }

                    }

                } else{
                    //Altrimenti no
                    System.out.println("[Dispatcher] No observers of type temperatura currently attached");

                }


            }else{

                //Caso observersPress
                if (observersPress.size()!=0){
                    //Se ci sono observers li notifico
                    for (int i=0;i<observersPress.size();i++){
                        try{
                            (observersPress.get(i)).notifyReading();
                            System.out.println("[Dispatcher] Observer pressione notified! Current state is "+reading.getValore());
                        }catch(RemoteException e){
                            System.err.println("[Dispatcher] "+e.getMessage());
                        }
                    }

                } else{
                    //Altrimenti no
                    System.out.println("[Dispatcher] No observers of type pressione currently attached");

                }

            }

        }catch(InterruptedException e){
            System.err.println("[Dispatcher] "+e.getMessage());
        }
        
    }

    @Override
    public Reading getReading() {
        
        return this.stato;

    }

    @Override
    public void attachObserver(Observer observer, String tipo) {

        try{
            if (tipo.compareTo("temperatura")==0){
                observersTemp.add(observer);
            }else{
                observersPress.add(observer);
            }

            System.out.println("[Dispatcher] Aggiunto observer del tipo "+tipo);
            observer.notifyObserver();
            System.out.println("[Dispatcher] Numero di observers correntemente attaccati:");
            System.out.println("Temperatura: "+ observersTemp.size());
            System.out.println("Pressione: "+ observersPress.size());
        }catch(RemoteException e){
            System.err.println("[Dispatcher] "+e.getMessage());
        }

    }


}
