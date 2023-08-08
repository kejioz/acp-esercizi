import stomp

import random,time

import pandas as pd
import numpy as np
from scipy import stats
from matplotlib import pyplot as plt

#Listener
class MyListener(stomp.ConnectionListener):

    #Costruttore vuoto per inizializzare lista di predictions
    def __init__(self):

        self.predictions=[]

    def on_message(self, frame):
        
        print('[CLIENT] Received response: %s' %frame.body)

        if "forecast" in frame.body:
            #Mi prendo la predizione (secondo campo della stringa)
            prediction = frame.body.split('-')[1]
            self.predictions.append(prediction)
    
    def get_predictions(self):

        return self.predictions


#Main
if __name__=='__main__':

    #Creo connection
    conn = stomp.Connection([('localhost',61613)],auto_content_length=False) ##Ricorda che il parametro della tupla delle connessioni è una lista

    #Set listener
    listener = MyListener()
    conn.set_listener('listener',listener=listener)

    #Connect e subscribe alla coda di risposta
    conn.connect(wait=True)
    conn.subscribe(destination='/queue/Response',id=1,ack='auto')

    #Send N richieste di forecast. Mi salvo gli anni però 
    forecast_reqs = 30
    years=[]
    for i in range (forecast_reqs):

        #Creo il messaggio
        request = 'forecast'
        year = random.randint(2021,2121)
        years.append(year)
        MSG = request+"-"+str(year)

        conn.send('/queue/Request', MSG)

        print('[CLIENT] Richiesta: '+MSG)

    #While true in cui aspetto le elaborazioni e faccio il plot
    while True:

        time.sleep(10)

        #Se ho tutte le prediction posso fare elaborazione e plot
        if (len(listener.get_predictions) == forecast_reqs):

            #Leggo il csv, cambio nomi alle colonne, skippo rows, tolgo 04
            nyc = pd.read_csv('https://www.ncei.noaa.gov/access/monitoring/climate-at-a-glance/city/time-series/USW00094728/tavg/1/4/1895-2023.csv?base_prd=true&begbaseyear=1991&endbaseyear=2020',skiprows=5)
            nyc.columns = ('Date', 'Temperature', 'Anomaly')
            nyc.Date = nyc.Date.floordiv(100) #toglie 04 dalla Date

            #Costruisco le serie pandas
            new_dates = pd.Series(years)    
            new_temps = pd.Series(listener.getPredictions(),dtype ='float') #Converte le string che avrei dal listener in float

            nan_series = pd.Series(np.nan, index=range(0, len(nyc.Date))) #serie nan per cambiare i valori, mi serve per azzerare il dataset

            newDates = pd.concat([nan_series, new_dates])
            newTemps = pd.concat([nan_series, new_temps])

            #Creo i subplots
            fig, ax = plt.subplots
            ax.scatter(nyc.Date,nyc.Temperature) #plot temp vs date
            ax.scatter(newDates, newTemps, alpha=0.25) #sono series pandas

            ax.legend(['Original','Predicted'])
            ax.set_xlabel('Years')
            ax.set_ylabel('Temperature [F°]')

            plt.show()

            #Se voglio salvare l'immagine del grafico e farla consumare da una webapp 
            plt.savefig('prediction.png')
            break

    #Chiudo la connessione
    conn.disconnect()
            