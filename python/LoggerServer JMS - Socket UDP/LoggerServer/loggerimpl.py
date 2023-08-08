from loggerskeleton import loggerskeleton

from threading import Lock

import sys

#Implementazione del lock
class loggerimpl(loggerskeleton):

    ##Costruttore in cui metto il lock per mutua esclusione
    def __init__(self, port):
        super().__init__(port)
        self.lock = Lock()

    ##RegistraDato in mutua esclusione
    def registraDato(self, dato):
        
        ###Acquisisco il lock
        self.lock.acquire()
        ###Stampo a video e salvo su file
        print('[LOGGER] Il dato da salvare è '+str(dato))
        with open('dati.txt',mode='a') as dati:
            dati.write(str(dato)+'\n')
            dati.close()
        print('[LOGGER] Dato %s salvato' %dato)

        self.lock.release()


#Main
if __name__ == '__main__':

    ##Creo uno skeleton sulla porta desiderata
    skeleton = loggerimpl(int(sys.argv[1]))
    skeleton.runSkeleton()
    print('[SERVER] SKELETON AVVIATO')