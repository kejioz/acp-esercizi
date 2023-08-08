from interface import Service

import sys

import socket

import threading

import queue

class Skeleton(Service):

    def __init__(self,port):
        self.port=port

    def deposita(self,message):
        pass

    def preleva(self):
        pass

    def runSkeleton(self):

        #Creazione socket e bind
        sock = socket.socket(socket.AF_INET,socket.SOCK_STREAM) 
        sock.bind(('127.0.0.1',int(self.port))) 
        print("[SERVER] Bindato sul porto "+self.port)

        #Listen
        sock.listen(5)

        while True:
            (conn,addr) = sock.accept()  ##La accept mi ritorna la tupla con connection e address

            #Processo
            print("[SERVER] Connessione stabilita, passo al processo")
            try:
                t= threading.Thread(target=func, args=(conn,self))
                t.start()
            except Exception:
                print('Error')


def func(self,conn,skeleton):

    mess = conn.recv(1024)

    method = mess.split("-")[0]

    if 'preleva' in method:
        val = skeleton.preleva()

    elif 'deposita' in method:
        
        val = mess.split("-")[1]
        skeleton.deposita(val)



class ServerImpl(Skeleton):

    def __init__(self,port):
        super().__init__(port)
        self.queue = queue.Queue(5)
    
    def deposita(self,message):
        self.queue.put(message)

    def preleva(self):
        val = self.queue.get(0)

        return val


if __name__ == '__main__':

    try:   

        PORT = sys.argv[1]

        print('[SERVER] Porto preso, faccio funzione di run')

        sk = ServerImpl(PORT)

        sk.runSkeleton()

    except IndexError:
        print("Metti come argomento il porto")

    sk = ServerImpl(PORT)

    sk.runSkeleton()

