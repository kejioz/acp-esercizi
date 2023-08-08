package Observer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Reading.Reading;
import Service.IDispatcher;
import Service.Observer;

public class ObserverImpl extends UnicastRemoteObject implements Observer{
    
    private IDispatcher dispatcher;
    private String tipo;
    private String nomefile;

    public ObserverImpl(IDispatcher dispatcher,String tipo, String nomefile) throws RemoteException{
        super();
        this.dispatcher=dispatcher;
        this.tipo=tipo;
        this.nomefile=nomefile;                   
    }

    public void notifyObserver() {
        
        System.out.println("[Observer "+tipo+"] Correctly attached!");

    }

    public void notifyReading(){

        try {
            
            System.out.println("[Observer "+tipo+"] Notified ! New Reading!");
            Reading reading = dispatcher.getReading();

            //Salvo il valore su file
            String valore = String.valueOf(reading.getValore());

            FileWriter fw = new FileWriter(nomefile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(valore);
            pw.flush();
            System.out.println("[Observer "+tipo+"] Saved value "+valore+" on file "+ nomefile);
            pw.close();
            bw.close();
            fw.close();

        }catch (RemoteException e){

            System.err.println("[Observer "+tipo+"] "+e.getMessage());

        } catch (IOException e) {
           
            System.err.println("[Observer "+tipo+"] "+e.getMessage());

        }

    }

    public String getTipo(){
        return this.tipo;
    }

}
