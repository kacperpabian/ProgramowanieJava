import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class PlayerObject extends UnicastRemoteObject implements Player {
    String name;
    JTextArea ta;


    protected PlayerObject(JTextArea tx) throws RemoteException {
        ta=tx;
    }

    protected PlayerObject(int port) throws RemoteException {
        super(port);
    }


    protected PlayerObject(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }


    public void setName(String name) throws RemoteException {
       this.name=name;
    }

    public String getName() throws RemoteException {
        return name;
    }

    public void sendOrder( String msg) throws RemoteException {
        ta.append(msg);
    }
}
