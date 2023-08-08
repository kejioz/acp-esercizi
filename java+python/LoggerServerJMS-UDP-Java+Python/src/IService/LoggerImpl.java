package IService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LoggerImpl implements ILogger {
    
    public LoggerImpl(){
        super();
    }

    @Override
    public synchronized void registraDato(int dato) {
        
        try{

            System.out.println("[LOGGER-IMPL] Ricevuto dato "+dato+", effettuo salvataggio file");

            FileWriter fw = new FileWriter ("dati.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(Integer.toString(dato));
            pw.flush();
            pw.close();
            bw.close();
            fw.close();
            System.out.println("[LOGGER-IMPL] Dato "+dato+" salvato su file dati.txt con successo");

        }catch(IOException e){
            e.printStackTrace();
        }

    }

}
