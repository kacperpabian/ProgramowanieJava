import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class CaptainMain {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CaptainGUI GUI = null;
                try {
                    GUI = new CaptainGUI();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
                GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GUI.setVisible(true);
            }
        });
    }
}
