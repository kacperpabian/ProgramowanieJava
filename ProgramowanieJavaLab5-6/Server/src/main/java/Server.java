import java.awt.*;
import java.awt.event.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.swing.*;
/*
 * Created by JFormDesigner on Tue Apr 03 03:01:08 CEST 2018
 */



/**
 * @author asd asd
 */
public class Server extends JFrame {
    Users users;
    Users stub;
    Registry registry;
    Captain captain;

    public Server() throws RemoteException, NotBoundException {
        initComponents();
        String name = "Users";
        users = new UsersObject();
        stub = (Users)UnicastRemoteObject.exportObject(users, 0);
        registry = LocateRegistry.getRegistry(1099);
        registry.rebind(name, stub);
    }

    public void updateUsers() throws RemoteException {
        DefaultListModel listModel = new DefaultListModel();
        for (int i=0;i<stub.getUsers().size();i++){
            listModel.addElement(stub.getUsers().get(i).getName());
        }
        PlayerList.setModel(listModel);
    }

    private void refreshButtonActionPerformed(ActionEvent e) {
        try {
            updateUsers();
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

    private void removeUserButtonActionPerformed(ActionEvent e) {
        try {
            captain = (Captain) registry.lookup("Captain");
        } catch (RemoteException e1) {
            e1.printStackTrace();
        } catch (NotBoundException e1) {
            e1.printStackTrace();
        }
        try {
            users.getUsers().get(PlayerList.getSelectedIndex()).sendOrder("____________________________________"+"\n");
            users.getUsers().get(PlayerList.getSelectedIndex()).sendOrder("Zostałeś wyrzucony z gry"+"\n");
            captain.setMsg("____________________________________"+"\n");
            captain.setMsg("Gracz :"+users.getUsers().get(PlayerList.getSelectedIndex()).getName()+" został wyrzucony z gry"+"\n");

            users.removeUser( PlayerList.getSelectedIndex());
            updateUsers();
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - asd asd
        scrollPane1 = new JScrollPane();
        PlayerList = new JList();
        label1 = new JLabel();
        removeUserButton = new JButton();
        refreshButton = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(PlayerList);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(10, 40, 190, 295);

        //---- label1 ----
        label1.setText("Lista graczy:");
        contentPane.add(label1);
        label1.setBounds(10, 10, 95, label1.getPreferredSize().height);

        //---- removeUserButton ----
        removeUserButton.setText("Usu\u0144 gracza");
        removeUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeUserButtonActionPerformed(e);
            }
        });
        contentPane.add(removeUserButton);
        removeUserButton.setBounds(310, 45, 150, 40);

        //---- refreshButton ----
        refreshButton.setText("Od\u015bwie\u017c liste");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshButtonActionPerformed(e);
            }
        });
        contentPane.add(refreshButton);
        refreshButton.setBounds(310, 120, 150, refreshButton.getPreferredSize().height);

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
    // Generated using JFormDesigner Evaluation license - asd asd
    private JScrollPane scrollPane1;
    private JList PlayerList;
    private JLabel label1;
    private JButton removeUserButton;
    private JButton refreshButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
