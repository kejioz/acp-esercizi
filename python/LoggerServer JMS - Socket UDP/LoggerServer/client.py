import sys  ##per argv per il dato e per la porta del server
import stomp ##per activemq


#Main
if __name__ == '__main__':

    #Dato e porta da terminale
    dato = sys.argv[1]
    porta = sys.argv[2]

    #Mi collego alla coda storage di ActiveMQ usando STOMP
    conn = stomp.Connection([('localhost',61613)],auto_content_length=False) #Creo connection, autocontent è per avere TextMess
    conn.connect(wait=True) #Starta la connection
    conn.send('/queue/storage',(dato+'-'+porta)) #Sendo direttamente senza dover creare sender
    print('[CLIENT] Send effettuata del dato ',dato,' e la porta ',porta)
    conn.disconnect() #Cleanup delle risorse
    