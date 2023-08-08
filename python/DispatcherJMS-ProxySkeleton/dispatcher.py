import sys,time

import stomp

import threading

import socket

from interface import Service

#Creo il listener
class MyListener(stomp.ConnectionListener):

    def __init__(self,conn,port):
        self.conn=conn
        self.port=port

    def on_message(self, frame):
        
        print("[DISPATCHER] Richiesta ricevuta: %s" %frame.body)

        proxy = ServiceProxy(int(self.port))    ##Devo fare il cast in int
        print("[DISPATCHER] Proxy avviato")

        #Creo un process che mi fa la richiesta, utilizzo la libreria multiprocess
        try:
            p = threading.Thread(target=proc_fun,args=(conn,proxy,frame.body))
            p.start()
        except Exception:
            print('error')
    


#Definisco la funzione del process
def proc_fun(conn, proxy, mess):
    
    #Mi prendo la richiesta dal messaggio usando il metodo split 
    request = mess.split('-')[0]

    #Se ho deposita
    if 'deposita' in request:           ##Posso fare anche if request == "deposita"

        id = mess.split('-')[1]
        result = proxy.deposita()
        print('[THREAD] Depositato %d' %id)
    ##Se ho preleva
    else:
        result = proxy.preleva()
        print('[THREAD] Prelevato %d '%result)

    #Mando il risultato
    conn.send('/queue/response',result)



#Definisco il Proxy che estende service
class ServiceProxy(Service):

    #Costruttore
    def __init__(self,port):
        self.port = port
        self.ip = '127.0.0.1'
        self.buffer_size = 1024
        print('[PROXY] Avviato')

    #Crea la socket tcp, invia la richiesta di preleva, aspetta la risposta, chiude la socket, ritornare il valore
    def preleva(self):

        #Creo la socket e mi connetto
        s = socket.socket(socket.AF_INET,socket.SOCK_STREAM) ##SOCKSTREAM TCP, DATAGRAM E' UDP
        s.connect((self.ip,int(self.port))) ##Ricorda che questo parametro è UNA TUPLA

        #Una volta che il server ha accettato la connessione, creo il messaggio
        message = "preleva"
        s.send(message.encode("utf-8"))
        print('[DISPATCHER] Ho inviato preleva')

        #Aspetto la risposta
        data = s.recv(self.buffer_size)
        print('[DISPATCHER] Ho prelevato '+str(data))

        #Chiudo la socket e ritorno i dati
        s.close()
        return data
    
    def deposita(self,mess):

        #Creo la socket e mi connetto
        s = socket.socket(socket.AF_INET,socket.SOCK_STREAM) ##SOCKSTREAM TCP, DATAGRAM E' UDP
        s.connect((self.ip,int(self.port))) ##Ricorda che questo parametro è UNA TUPLA

        #Una volta che il server ha accettato la connessione, creo il messaggio
        message = "deposita-"+str(mess)
        s.send(message.encode("utf-8"))
        print('[DISPATCHER] Ho inviato deposita di '+str(mess))

        #Aspetto la risposta
        data = s.recv(self.buffer_size)
        print('[DISPATCHER] Ho ricevuto ack del deposito di '+str(mess))

        #Chiudo la socket e ritorno i dati
        s.close()
        return data



#Definisco il main
if __name__ == "__main__":

    #Mi devo prendere il porto da terminale
    try:

        PORT= sys.argv[1]


    except IndexError:
        print ("please, add a port arg")

    #Creo la connection con la lista di tuple
    conn = stomp.Connection([('127.0.0.1',61613)])

    #Setto il listener
    conn.set_listener('listener',MyListener(conn,PORT))

    #Mi connetto
    conn.connect(wait=True) ##wait = true aspetta che la conn sia completata

    #Faccio la subscribe ad una destinatio
    conn.subscribe(destination='/queue/response', id=1, ack='auto')

    print("[DISPATCHER] Waiting for requests...")

    while True:
        time.sleep(60)