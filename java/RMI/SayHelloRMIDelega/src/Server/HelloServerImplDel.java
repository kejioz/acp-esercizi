package Server;

import java.rmi.RemoteException;

import Service.Hello;

public class HelloServerImplDel implements Hello{

    public HelloServerImplDel(){
        super(); //costruttore superiore a vuoto, devo esportarla a mano
    }

    public String sayHello() throws RemoteException{

        System.out.println("sayHello() Invocato");

        return "Hello!";

    }

}