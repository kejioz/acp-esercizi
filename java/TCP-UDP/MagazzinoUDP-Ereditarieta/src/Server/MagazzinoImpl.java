package Server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Coda.*;

public class MagazzinoImpl extends MagazzinoSkeleton {
    
    private static int numerofile=0;
    private Coda codalaptop;
    private Coda codasmartphone;

    public MagazzinoImpl(){
        
        this.codalaptop = new CodaSemaphore(new CodaCircolare(5));
        this.codasmartphone = new CodaSemaphore(new CodaCircolare(5));

    }

    public void deposita(String articolo,int id){

        if (articolo.compareTo("laptop")==0){
            codalaptop.inserisci(id);
            System.out.println("[MagazzinoImpl] Laptop dall'id "+id+" depositato nel magazzino");
        }
        else if (articolo.compareTo("smartphone")==0){
            codasmartphone.inserisci(id);
            System.out.println("[MagazzinoImpl] Smartphone dall'id "+id+" depositato nel magazzino");
        }

    }

    public int preleva(String articolo){

        int id=0;

        if (articolo.compareTo("laptop")==0){
            id= codalaptop.preleva();
            System.out.println("[MagazzinoImpl] Laptop dall'id "+id+" prelevato dal magazzino");
        }
        else if (articolo.compareTo("smartphone")==0){

            id = codasmartphone.preleva();
            System.out.println("[MagazzinoImpl] Smartphone dall'id "+id+" prelevato dal magazzino");
        }

        //Salvataggio su file
        try{
            FileWriter fw = new FileWriter("file"+numerofile,false);
            numerofile++;
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(id);
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
            System.out.println("[MagazzinoImpl] Log dell'articolo "+articolo+" dall'id "+id+" salvato su file "+ (numerofile-1));
        }catch(IOException e){
            System.err.println("[MagazzinoImpl] IoException");
        }

        return id;

    }

}
