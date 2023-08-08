from threading import Lock,Condition

class coda:

    def __init__(self,size):
        
        self.data = []
        self.elems = 0
        self.size = size
        self.lock = Lock()
        self.mess_disp = Condition(lock=self.lock)
        self.spa_disp = Condition(lock=self.lock)

    def empty(self):

        if (self.elems==0):
            return True
        return False
    
    def full(self):

        if (self.elems==self.size):
            return True
        return False
    
    
    def inserisci(self,comando):

        stato = ''
        if (self.lock.locked):
            stato = 'bloccato'
        else:
            stato = 'non bloccato'
        print(f'Appena entrato in inserisci, stato: {stato} ')
        self.lock.acquire()
            
        try:

            while self.full():
                
                self.spa_disp.wait()
             

            self.data.append(comando)
            self.elems = self.elems+1
            print(f'[SENSOR] INSERITO {comando}')
            print(self.data)

            self.mess_disp.notify_all()

        except Exception as e:

            print(e)

        finally:

            self.lock.release()


    def preleva(self):

        self.lock.acquire()

        try:

            while self.empty():
                
                self.mess_disp.wait()

            comando =self.data.pop()
            self.elems = self.elems-1
            print(f'[SENSOR] PRELEVATO {comando}')
            self.spa_disp.notify_all()

            return comando

        except Exception as e:

            print(e)

        finally:

            self.lock.release()