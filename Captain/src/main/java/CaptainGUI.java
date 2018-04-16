import java.awt.*;
import java.awt.event.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.*;
/*
 * Created by JFormDesigner on Fri Apr 06 19:34:55 CEST 2018
 */



/**
 * @author asd asd
 */
public class CaptainGUI extends JFrame {
    String host = "localhost";
    int pkt=0;
    Random random = new Random();
    String orders= "";
    String orderValue= "";
    Thread t1;


    int port = 1099;
    Users users;
    Registry registry;
    public  Map<String, List<String>> ansver = new HashMap<String, List<String>>();
    Captain captain;
    Captain stub;
    boolean start= true;

    public CaptainGUI() throws RemoteException, NotBoundException {
        initComponents();
        captain =new CaptainObj(ansver,logArea);
        registry = LocateRegistry.getRegistry( port);
         users = (Users) registry.lookup("Users");
        stub = (Captain) UnicastRemoteObject.exportObject(captain, 0);
        registry.rebind("Captain", stub);
    }
    public void updateUsers() throws RemoteException, NotBoundException {
        users = (Users) registry.lookup("Users");
        DefaultListModel listModel = new DefaultListModel();
        for (int i=0;i<users.getUsers().size();i++){
            listModel.addElement(users.getUsers().get(i).getName());
        }
        PlayerList.setModel(listModel);
    }

    private void refreshButtonActionPerformed(ActionEvent e) {
        try {
            updateUsers();
        } catch (RemoteException e1) {
            e1.printStackTrace();
        } catch (NotBoundException e1) {
            e1.printStackTrace();
        }
    }

    public void sendOrder(){
        try {
            orders = captain.getNames().get(random.nextInt(captain.getNames().size()));
            if(captain.getDevices().get(orders).get(random.nextInt(captain.getDevices().get(orders).size()))!=null) {
                orderValue = captain.getDevices().get(orders).get(random.nextInt(captain.getDevices().get(orders).size()));
            }
            else orderValue =String.valueOf(random.nextInt(100));

            logArea.append("____________________________________"+"\n");
            logArea.append("Urządzenie: "+orders+" wykonaj:"+orderValue+"\n");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void startButtonActionPerformed(ActionEvent e)  {
        logArea.append("STARTUJEMY"+"\n\n");
        try {
        for (int k=0; k<users.getUsers().size();k++){
            users.getUsers().get(k).sendOrder("STARTUJEMY"+"\n\n");
        }
        } catch (RemoteException c) {
            c.printStackTrace();
        }

        start=true;
        pkt=0;
        pktTextField.setText(String.valueOf(pkt));
        t1=new Thread (new Runnable() {
            public void run() {
                while(start){
                    sendOrder();
                    try {
                        Thread.sleep(7000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    if(ansver.containsKey(orders)){
                        if(ansver.get(orders).contains(orderValue)) pkt++; }
                    else {
                        pkt--; }

                    orders= "";
                    orderValue= "";
                    ansver.clear();
                    pktTextField.setText(String.valueOf(pkt));
                }
            }
        });
        t1.start();

    }

    private void endButtonActionPerformed(ActionEvent e) {
        t1.stop();
        logArea.append("____________________________________"+"\n");
        logArea.append("KONIEC GRY"+"\n");
        logArea.append("Liczba punktów: "+pkt+"\n");
        try {
            for (int k=0; k<users.getUsers().size();k++){
                users.getUsers().get(k).sendOrder("____________________________________"+"\n");
                users.getUsers().get(k).sendOrder("KONIEC GRY"+"\n");
                users.getUsers().get(k).sendOrder("Liczba punktów: "+pkt+"\n");
            }
        } catch (RemoteException c) {
            c.printStackTrace();
        }
        pkt=0;
        pktTextField.setText(String.valueOf(pkt));
        start=false;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - PAcper Kabian
        scrollPane1 = new JScrollPane();
        PlayerList = new JList();
        ListLabel = new JLabel();
        startButton = new JButton();
        endButton = new JButton();
        refreshButton = new JButton();
        scrollPane2 = new JScrollPane();
        logArea = new JTextArea();
        LogLabel = new JLabel();
        pktLabel = new JLabel();
        pktTextField = new JTextField();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(PlayerList);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(25, 35, 165, 335);

        //---- ListLabel ----
        ListLabel.setText("Lista Graczy:");
        contentPane.add(ListLabel);
        ListLabel.setBounds(25, 10, 145, ListLabel.getPreferredSize().height);

        //---- startButton ----
        startButton.setText("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startButtonActionPerformed(e);
            }
        });
        contentPane.add(startButton);
        startButton.setBounds(new Rectangle(new Point(250, 35), startButton.getPreferredSize()));

        //---- endButton ----
        endButton.setText("Stop");
        endButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                endButtonActionPerformed(e);
            }
        });
        contentPane.add(endButton);
        endButton.setBounds(410, 35, 86, 31);

        //---- refreshButton ----
        refreshButton.setText("Od\u015bwie\u017c liste");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshButtonActionPerformed(e);
            }
        });
        contentPane.add(refreshButton);
        refreshButton.setBounds(25, 370, 165, 31);

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(logArea);
        }
        contentPane.add(scrollPane2);
        scrollPane2.setBounds(220, 175, 295, 190);

        //---- LogLabel ----
        LogLabel.setText("Dziennk:");
        contentPane.add(LogLabel);
        LogLabel.setBounds(new Rectangle(new Point(225, 140), LogLabel.getPreferredSize()));

        //---- pktLabel ----
        pktLabel.setText("Punkty:");
        contentPane.add(pktLabel);
        pktLabel.setBounds(230, 90, 65, pktLabel.getPreferredSize().height);
        contentPane.add(pktTextField);
        pktTextField.setBounds(295, 90, 70, 25);

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
    private JScrollPane scrollPane1;
    private JList PlayerList;
    private JLabel ListLabel;
    private JButton startButton;
    private JButton endButton;
    private JButton refreshButton;
    private JScrollPane scrollPane2;
    private JTextArea logArea;
    private JLabel LogLabel;
    private JLabel pktLabel;
    private JTextField pktTextField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
