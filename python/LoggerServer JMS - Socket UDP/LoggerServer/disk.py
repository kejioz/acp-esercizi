import stomp #Per comunicazione activemq
import socket #Per comunicazione UDP
import threading
import time

from loggerproxy import loggerproxy

#Definisco il listener
class DiskListener(stomp.ConnectionListener):

    ##Costruttore
    def __init__(self,conn):
        self.conn=conn
    
    ##On message
    def on_message(self, frame):
        
        ###Devo avviare un thread che estrae dal frame le informazioni
        print('[LISTENER] Ricevuto messaggio, avvio thread per estrarre informazioni')
        thread = ListenerThread(frame)
        thread.start()

#Definisco il thread avviato dal listener
class ListenerThread(threading.Thread):

    ##Costruttore
    def __init__(self,frame):
        super().__init__()
        self.frame=frame

    ##run
    def run(self):

        ###Devo estrarre dal frame le informazioni
        messaggio = self.frame.body
        dato = messaggio.split('-')[0]
        porta = messaggio.split('-')[1]
        print('[THREAD] Estratto dato %s e la porta %s' %(dato,porta))

        ###Devo invocare il metodo registraDato di ILogger. Contatto il proxy
        proxy = loggerproxy(dato,porta)
        print('[THREAD] Richiesto al proxy di inoltrare allo skeleton il dato ',dato)
        proxy.registraDato(str(dato))



#Main
if __name__ == '__main__':

    conn = stomp.Connection([('localhost',61613)],auto_content_length=False) #Creo connection, autocontent è per avere TextMess
    conn.set_listener('DiskListener', DiskListener(conn)) #Creo il listener a cui passo la conn
    conn.connect(Wait=True) #Mi connetto
    conn.subscribe(destination='/queue/storage',id=1,ack='auto')   #Passo la destination
    time.sleep(60)
    conn.disconnect()
    
