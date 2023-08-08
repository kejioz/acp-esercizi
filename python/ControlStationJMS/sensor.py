import stomp

import time,sys

import threading

from coda import coda

#Funzione del processo per inserire i comandi nella coda
def inserisci_comando(queue,comando):

    queue.inserisci(comando)
    


#Listener che avvia sul receive dei messaggi i thread per inserire i comandi
class MyListener(stomp.ConnectionListener):

    def __init__(self,queue):
        super().__init__()
        self.queue=queue

    def on_message(self, frame):

        #Prendo il contenuto del messaggio, che è il comando
        comando = frame.body

        #Devo creare un nuovo thread per gestire la coda in base al messaggio
        p = threading.Thread(target=inserisci_comando,args=(queue,comando))
        print(f'[SENSOR] COMANDO {comando} RICEVUTO, STARTO UN PROCESSO PER LA GESTIONE DELLA CODA')
        p.start()



#Funzione del processo per svuotare la coda e salva su file i comandi prelevati
def svuota_coda(queue):

    #Fino all'infinito - svuoto TUTTA la coda salvando su file le cose prelevate e poi aspetto 10 secondi
    while (True):

        time.sleep(10)

        while not queue.empty():

            with open(file='CmdLog.txt',mode='a') as log:
                comando = queue.preleva()
                print(comando)
                log.write(comando+'\n')
                print(f'[texecutor] salvato comando {comando} su file cmdlog.txt')



#Main
if __name__=='__main__':

    #Creo connection
    connection = stomp.Connection([('localhost',61613)],auto_content_length=False)

    #Mi connetto
    connection.connect(wait=True)

    #Mi subscribo
    connection.subscribe(destination=('/topic/topic'),id=1,ack='auto')

    #Creo la coda, di dimensione D
    D = sys.argv[1]
    queue = coda(size=int(D))

    #Creo il listener, a cui passo la coda, per inserire i comandi
    listener = MyListener(queue=queue)

    #Setto il listener
    connection.set_listener('listener',listener=listener)

    #Creo il TExecutor che svuota la coda ogni 10 secondi
    executor = threading.Thread(target=svuota_coda,args=(queue, ))
    executor.start()

    #Ascolto i messaggi
    while True:
        time.sleep(120)
