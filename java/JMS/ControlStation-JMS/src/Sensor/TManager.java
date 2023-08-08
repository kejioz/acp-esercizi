package Sensor;

import Coda.CodaCircolare;

public class TManager extends Thread {
    
    private String comando;
    private CodaCircolare coda;

    public TManager(String cmd,CodaCircolare coda){
        this.comando=cmd;
        this.coda=coda;
        System.out.println("[TManager] Inizializzato");
    }

    @Override
    public void run() {
        coda.inserisci(comando);
        System.out.println("[TManager] Comando "+comando+" inserito");
    }

}
