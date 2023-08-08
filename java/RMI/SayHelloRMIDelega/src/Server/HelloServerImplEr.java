package Server;

import java.rmi.RemoteException;

import Service.Hello;

import java.rmi.server.UnicastRemoteObject;


public class HelloServerImplEr extends UnicastRemoteObject implements Hello{
    
    public HelloServerImplEr () throws RemoteException{
        super(); //costruttore a vuoto, devo esportare a mano
    }

    public String sayHello() throws RemoteException{
        System.out.println("sayHello() Invocato");

        return "Hello!";
    }

}
