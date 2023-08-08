public class Test {

    public static void main(String[] args) {
        
        Coda coda = new CodaCircolare(10);

        //creo una coda Lock
        Coda codal = new CodaSem(coda);

        Worker [] threads = new Worker[100];

        for(int i=0; i<10; i++){

            if(i%2==0){
                threads[i] = new Worker(codal, true);

            } else {
                threads[i] = new Worker(codal, false);
            }

            threads[i].start();
        }

        for(int i=0; i<10; i++){

            try{
                threads[i].join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }

        }


    }
    
}