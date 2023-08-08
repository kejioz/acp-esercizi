from collections.abc import Callable, Iterable, Mapping
from typing import Any
import stomp
import multiprocessing
from loggerproxy import loggerproxy
import time

#Listener
class DiskListener(stomp.ConnectionListener):

    def __init__(self) :
        super().__init__()
        print('[LISTENER] Inizializzato')
    
    def on_message(self, frame):
        print('[LISTENER] Messaggio ricevuto, avvio un thread per estrarlo')
        p = DiskProcess(frame=frame)
        p.start()

class DiskProcess(multiprocessing.Process):
    def __init__(self,frame):
        super(DiskProcess,self).__init__()
        self.frame=frame

    def run(self) :
        #Stringa
        mess = self.frame.body

        #Dato e porta
        dato = (mess.split('-')[0])
        porta = int (mess.split('-')[1])
        print(f'[THREAD] Estratto dato {dato} e porta {porta}')

        #Creo il proxy e sendo
        proxy = loggerproxy(porta=porta)
        proxy.registraDato(dato)


#Main
if __name__ == '__main__':

    #Creo la connection
    connection = stomp.Connection([('localhost',61613)],auto_content_length='false') #Per avere text messages
    #Creo il listener
    listener = DiskListener()
    #Setto il listener
    connection.set_listener('listener',listener=listener)
    connection.connect(wait=True)

    connection.subscribe(('/queue/storage'),id=1,ack='auto')
    print('[DISK] Listener settato')
    time.sleep(120)
    #Cleanup
    connection.disconnect

