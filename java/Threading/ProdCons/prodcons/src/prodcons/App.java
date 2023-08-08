package prodcons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) {
        //creazione del buffer condiviso
        Buffer buf = new Buffer();
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        int choice=0,id=1;

        while (true){
            System.out.println("0 (C) / 1 (P) >> ");
            
            try{
                //input da tastiera
                choice=Integer.parseInt(stdin.readLine());
            }catch(IOException e){
                e.printStackTrace();
            }
            if (choice==0){
                Consumer c = new Consumer(buf," consumer__"+id);
                c.start();
            }else{
                Producer p = new Producer(buf, " producer__"+id);
                p.start();
            }
            id=id+1;

            }
        }
    }

