package Server;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;

import java.util.Random;
import java.util.concurrent.Semaphore;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

import Service.Sportello;

public class SportelloImpl extends UnicastRemoteObject implements Sportello{

    protected SportelloImpl() throws RemoteException{
        max_concurrency = new Semaphore(3);
        max_requests=new Semaphore(5);
    }

    private static final long serialVersionUID= 10l;

    private Semaphore max_concurrency;
    private Semaphore max_requests;

    @Override
    public boolean serviRichiesta(int idcliente)throws RemoteException{

        boolean result = false;

        //Facendo questo provo ad acquisire, se posso acquisisco il maxrequests e restituisco true
        //Se non posso restituisco false e non acquisisco il lock
        if (!max_requests.tryAcquire()){
            System.out.println("[Sportello] Raggiunto limite richieste");
            System.out.println("[Sportello] Richiesta da "+idcliente+"abortita");
            return result;
        }

        try{
        //Se mi trovo qua allora vuol dire che ho avuto il lock su max requests e posso entrare in concurrency
            max_concurrency.acquire();

            Random rand = new Random();

            Thread.sleep(((rand.nextInt(5))+1)*1000);

            FileWriter writer = new FileWriter("idclienti.txt",true); //true mette in append
            BufferedWriter bw= new BufferedWriter(writer);
            PrintWriter pw= new PrintWriter(bw);

            pw.println(idcliente);
            pw.flush();
            pw.close();
            bw.close();
            writer.close();

            result= true;
            System.out.println("[Sportello] Servita richiesta da "+idcliente);
        }catch(InterruptedException e){
            e.printStackTrace();
            result=false;
        }catch(IOException e){
            e.printStackTrace();
            result=false;
        }finally{
            max_concurrency.release();  //rilascio i locks
            max_requests.release(); //rilascio i locks
        }

        return result;
    }

}