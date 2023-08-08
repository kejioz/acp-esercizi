import stomp

import sys,time

import random


#Main
if __name__=='__main__':

    #Creo la connection
    connection = stomp.Connection([('localhost',61613)],auto_content_length=False)

    #Mi connetto
    connection.connect(wait=True)

    #Faccio N iterazioni mandando messaggi
    N = int(sys.argv[1])
    for i in range(N):

        rand = random.randint(0,2)
        comando = ''

        if (rand==0):
            comando = 'startSensor'
        elif (rand==1):
            comando = 'stopSensor'
        else:
            comando = 'read'

        connection.send(destination=('/topic/topic'),body=(comando))
        print(f'[CONTROL-STATION] Mandato comando di {comando} ')
        time.sleep(1)
    
    
        