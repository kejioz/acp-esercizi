package Service;

import java.rmi.Remote;

public interface ISubscriber extends Remote{
    
    public void notifyAlert(int criticality);

}
