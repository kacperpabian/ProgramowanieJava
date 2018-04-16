import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Users extends Remote
{
    public void addUser(Player player) throws RemoteException;
    public List<Player> getUsers() throws RemoteException;
    public void removeUser(int index) throws RemoteException;
}