import socket,sys,threading

from loggerserver import loggerserver

#Definisco il thread worker che fa l'upcall
class skeletonThread(threading.Thread):

    ##Costruttore per upcall che poi comunica con proxy
    def __init__(self,skeleton,client):
        super().__init__()
        self.skeleton=skeleton
        self.client=client
        print('[THREAD] Startato')
    
    ##Run
    def run(self):
        print('[THREAD] Eseguo')
        ###Devo ricevere il messaggio
        dato = self.client.recv(1024)
        dato = dato.decode()

        ###Faccio l'upcall
        self.skeleton.registraDato(str(dato))
        print('[THREAD] Upcall effettuata')

        ###Chiudo connessione
        self.client.close()


#Skeleton
class loggerskeleton(loggerserver):

    ##Costruttore a cui passo la porta
    def __init__(self,port):
        self.port = port

    ##RunSkeleton
    def runSkeleton(self):

        ###Creo socket TCP, poi la bindo sul porto desiderato
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        sock.bind(('localhost',self.port))

        ###Mi metto in listen
        sock.listen(50)
        print('[SKELETON] Listening..')

        ###Ciclo loop
        while True:
            client,address = sock.accept() ###Ritorna la socket che rappresenta la conn e l'indirizzo
            print ('[SKELETON] Connessione istanziata con ',address)

            ###Starto il thread
            thread = skeletonThread(skeleton=self,client=client)
            print ('[SKELETON] Thread inizializzato')
            skeletonThread.start(self=thread)
            print ('[SKELETON] Thread startato')

    ##registraDato da overridare
    def registraDato(self, dato):
        pass
