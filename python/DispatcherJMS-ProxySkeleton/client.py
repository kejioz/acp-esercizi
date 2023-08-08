import stomp

import random
import time

#Definisco il listener
class MyListener(stomp.ConnectionListener):

    ##Override di on_message
    def on_message(self, frame):
        
        print('[CLIENT] Received response: %s',frame.body)


#Main
if __name__=='__main__':

    #Creo la connection con la lista di tuple
    conn = stomp.Connection([('127.0.0.1',61613)])

    #Setto il listener
    conn.set_listener('listener',MyListener())

    #Mi connetto
    conn.connect(wait=True) ##wait = true aspetta che la conn sia completata

    #Faccio la subscribe ad una destinatio
    conn.subscribe(destination='/queue/response', id=1, ack='auto')

    for i in range (10):

        if (i%2==0):
            request = 'deposita'
            id = random.randint(1,100)
            MSG = request+"-"+str(id)

        else:
            MSG='preleva'
        
        conn.send('/queue/response',MSG)

        print ('[CLIENT] Request: ',MSG)
    
    #....Qui c'è bisogno di fare un while true per far rimanere il client in attesa di messaggi 

    ##Se impostiamo un numero finito di interazioni, allora fare conn.disconnect()