import sys
import stomp


#Main
if __name__== '__main__':

    dato = (sys.argv[1])  #Dato
    ##Controllo sul dato
    if (int(dato)>100 or int(dato)<0):
        print("Inserire un dato maggiore di 0 e minore di 100")
        exit()

    porta = (sys.argv[2]) #Porta del LoggerServer

    #Connessione a JMS
    connection = stomp.Connection([('localhost',61613)]) #Creata la connessione
    connection.connect(wait=True) #Mi connetto in wait
    connection.send(('/queue/storage'),(dato+"-"+porta),content_type='text') #Send
    print(f'[CLIENT] Send effettuata del dato {dato} e della porta {porta}')
    connection.disconnect #Cleanup risorse
