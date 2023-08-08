from service import Service

import sys,socket

import multiprocess as mp

import stomp

import random,time

import pandas as pd
import numpy as np
from scipy import stats
from matplotlib import pyplot as plt

#Funzione process
def proc_fun(c,service):

    data = c.recv[1024]
    result = ""
    if "forecast" in str(data.decode()):

        year = str(data.decode()).split('-')[1]
        result = service.forecast(year)

    string_to_send = str(result)+"\n"
    c.send(string_to_send.encode())

    c.close()


#Skeleton
class ServiceSkeleton(Service):

    def __init__(self,port):
        self.port=port

    def forecast(self, message):
        pass

    def run_Skeleton(self):

        s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
        s.bind(('localhost',int(self.port)))
        s.listen(5)

        print('Socket listening')

        while True:
            (c,addr) = s.accept

            p = mp.Process(target=proc_fun,args=(c,self))
            p.start()

class ServiceImpl(ServiceSkeleton):

    def forecast(self,year):
            nyc = pd.read_csv('https://www.ncei.noaa.gov/access/monitoring/climate-at-a-glance/city/time-series/USW00094728/tavg/1/4/1895-2023.csv?base_prd=true&begbaseyear=1991&endbaseyear=2020',skiprows=5)
            nyc.columns = ('Date', 'Temperature', 'Anomaly')
            nyc.Date = nyc.Date.floordiv(100) #toglie 04 dalla Date
            linear_regression = stats.linregress(x=nyc.Date, y=nyc.Temperature)
            prediction = linear_regression.slope * int(year) +linear_regression.intersect
            return prediction


if __name__ == '__main__':

    PORT = sys.argv[1]

    serviceImpl = ServiceImpl(int(PORT))
    serviceImpl.run_Skeleton()