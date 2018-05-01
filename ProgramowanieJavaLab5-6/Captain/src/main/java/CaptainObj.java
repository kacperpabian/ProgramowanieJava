import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaptainObj implements Captain {
    Map<String, List<String>> map;
    JTextArea tx;
    public CaptainObj(Map<String, List<String>> map, JTextArea tx){
        this.map=map;
        this.tx=tx;

    }
   public  Map<String, List<String>> devices = new HashMap<String, List<String>>();
   public  List<String> deviceNames=new ArrayList<String>();
   public void setDevice(String device, String value) throws RemoteException {
        if(!devices.containsKey(device)){
            List<String> values= new ArrayList();
            values.add(value);
            deviceNames.add(device);
            devices.put(device,values);
        }else{
            if(!devices.get(device).contains(value)){
                devices.get(device).add(value);
            }
        }
    }

    public Map<String, List<String>> getDevices() {
       return devices;
    }

    public List<String> getNames() throws RemoteException {
        return deviceNames;
    }

    public void setAnswer(String device, String value) throws RemoteException {
        if(!map.containsKey(device)){
            List<String> values= new ArrayList();
            values.add(value);
            map.put(device,values);
        }else{
            if(!map.get(device).contains(value)){
                map.get(device).add(value);
            }
        }

    }

    public void setMsg(String msg) throws RemoteException {
        tx.append(msg);
    }
}
