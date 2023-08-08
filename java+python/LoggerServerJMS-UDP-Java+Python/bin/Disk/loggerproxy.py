import socket


class loggerproxy():

    #Costruttore
    def __init__(self,porta):
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sock.connect(('localhost',porta))
        print(f'[PROXY] Proxy inizializzato, comunico con lo skeleton sul porto {porta}')

    def registraDato(self,dato):
        self.sock.send(dato.encode('UTF-8'))
        print(f'[PROXY] Mandato {dato} allo skeleton')
        self.sock.close()