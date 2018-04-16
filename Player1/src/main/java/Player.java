import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Player extends Remote {
    public void setName(String name) throws RemoteException;
    public String getName() throws RemoteException;
    public void sendOrder( String msg) throws RemoteException;
}
