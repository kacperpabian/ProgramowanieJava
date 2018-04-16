import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class UsersObject implements Users
{
    private List<Player> users= new ArrayList();



    public void addUser(Player player) throws RemoteException {
        users.add(player);
    }

    public List<Player> getUsers() throws RemoteException {
        return users;
    }

    public void removeUser(int index) throws RemoteException {
        users.remove(index);
    }

}