import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Captain extends Remote {

    public void setDevice(String device, String value)throws RemoteException;
    public Map<String, List<String>> getDevices() throws RemoteException;
    public List<String> getNames() throws RemoteException;
    public void setAnswer(String device,String value) throws RemoteException;
    public void setMsg(String msg)throws RemoteException;
}
