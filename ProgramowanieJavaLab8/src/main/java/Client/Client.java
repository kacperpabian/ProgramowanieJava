package Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client extends javax.swing.JFrame {
    private JTextArea textAreaMessages;
    private JPanel MainPanel;
    private JTextField textFieldMessage;
    private JButton buttonSend;
    private JButton buttonConnect;
    private JTextField textFieldAddress;
    private JTextField textFieldPort;
    private JButton buttonDisconnect;
    private JLabel labelAddress;
    private JLabel labelPort;
    private JTextField textFieldUsername;
    private JLabel labelUsername;
    private JLabel labelMessage;

    ArrayList<String> users = new ArrayList();
    int port = 2222;
    Boolean isConnected = false;
    String username, address = "localhost";
    Socket sock;
    BufferedReader reader;
    PrintWriter writer;


    public Client() {
        buttonConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectActionPerformed(e);
            }
        });
    }

    private void connectActionPerformed(java.awt.event.ActionEvent evt) {
        if (isConnected == false)
        {
            username = textFieldUsername.getText();
            try
            {
                sock = new Socket(address, port);
                InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamReader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush();
                isConnected = true;
            }
            catch (Exception ex)
            {
                textAreaMessages.append("Cannot Connect! Try Again. \n");
            }

            //ListenThread();

        } else if (isConnected == true)
        {
            textAreaMessages.append("You are already connected. \n");
        }
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }  catch(Exception e){
        }
        JFrame clientFrame =new JFrame ("Client");
        Client client=new Client();
        clientFrame.setContentPane(client.MainPanel);
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.pack();
        clientFrame.setVisible(true);
        clientFrame.setLocationRelativeTo(null);
    }
}
