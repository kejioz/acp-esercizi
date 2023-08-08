from loggerserver import loggerserver

import socket

class loggerproxy(loggerserver):

    ##Costruttore
    def __init__(self,dato,port):
        self.dato=dato
        self.port=port

    ##Override metodo
    def registraDato(self, dato):
        
        ###Creo la socket
        sock = socket.socket(socket.AF_INET,socket.SOCK_STREAM) ##Creo socket TCP senza bindare

        ###Mi connetto allo skeleton
        sock.connect(('localhost',int(self.port)))

        ###Mando il dato
        sock.send(dato.encode("utf-8"))
        print('[PROXY] Mandato allo skeleton ',str(dato))

        ###Chiudo connessione
        sock.close()