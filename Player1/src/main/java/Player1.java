import java.awt.*;
import java.awt.event.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;
import ziarenka.*;
/*
 * Created by JFormDesigner on Sat Apr 07 11:30:45 CEST 2018
 */



/**
 * @author asd asd
 */
public class Player1 extends JFrame {
    String host = "localhost";
    int port = 1099;
    Captain captain;
    PlayerObject player;
    public Player1() throws RemoteException, NotBoundException {
        initComponents();

        player= new PlayerObject(playerBean1.getTextDevice4());
        String myName = "Player1";
        player.setName(myName);
        String name = "Users";
        Registry registry = LocateRegistry.getRegistry(host, port);
        Users users = (Users) registry.lookup(name);
        captain = (Captain) registry.lookup("Captain");
        users.addUser(player);
        if(playerBean1.isDevSet1()){
            captain.setDevice(playerBean1.getDevice1(),null);
        }
        if(playerBean1.isDevSet2()){
            captain.setDevice(playerBean1.getDevice2(),String.valueOf(playerBean1.getDevice21Value()));
            captain.setDevice(playerBean1.getDevice2(),String.valueOf(playerBean1.getDevice22Value()));
        }
        if(playerBean1.isDevSet3()){
            captain.setDevice(playerBean1.getDevice3(),playerBean1.getDevice4());
        }
    }

    private void ansverButtonActionPerformed(ActionEvent e) {
        try {
        if(playerBean1.isDevSet1()){
            captain.setAnswer(playerBean1.getDevice1(),playerBean1.getTextDevice1());

        }
        if(playerBean1.isDevSet2()){
            captain.setAnswer(playerBean1.getDevice2(),playerBean1.getSelectedDevice2value());
        }
        if(playerBean1.isDevSet3()){
            captain.setAnswer(playerBean1.getDevice3(),playerBean1.getDevice4());
        }
        captain.setMsg("____________________________________"+"\n");
        captain.setMsg("Player1 wykonał rozkaz"+"\n");

        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - PAcper Kabian
        ansverButton = new JButton();
        playerBean1 = new PlayerBean();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- ansverButton ----
        ansverButton.setText("Wy\u015blij odpowied\u017a");
        ansverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ansverButtonActionPerformed(e);
            }
        });
        contentPane.add(ansverButton);
        ansverButton.setBounds(115, 205, 250, 45);

        //---- playerBean1 ----
        playerBean1.setDevice1("Motylopendownik");
        playerBean1.setDevice2("Wykorzystanie zbigniewonap\u0119dzarki");
        playerBean1.setDevice21Value(25);
        playerBean1.setDevice22Value(50);
        playerBean1.setDevice4("W\u0142\u0105cz");
        playerBean1.setDevSet2(true);
        playerBean1.setDevSet3(true);
        playerBean1.setDevSet4(true);
        playerBean1.setDevSet1(true);
        playerBean1.setDevice3("Skolopendromorfowniczka");
        contentPane.add(playerBean1);
        playerBean1.setBounds(new Rectangle(new Point(5, 5), playerBean1.getPreferredSize()));

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - PAcper Kabian
    private JButton ansverButton;
    private PlayerBean playerBean1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
