package Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server extends javax.swing.JFrame {
    private JTextArea textAreaInfo;
    private JPanel MainPanel;
    private JButton buttonStart;
    private JButton buttonEnd;
    private JButton buttonOnlineUsers;
    private JButton buttonClear;

    ArrayList clientOutputStreams;
    ArrayList<String> users;


    public Server() {
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButtonAction(e);
            }
        });
        buttonEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endButtonAction(e);
            }
        });
        buttonOnlineUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeUsersAction(e);
            }
        });
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearButtonAction(e);
            }
        });
    }

    private void startButtonAction (ActionEvent e)
    {
        Thread starter = new Thread(new ServerStart());
        starter.start();

        textAreaInfo.append("Server started...\n");
    }

    private void endButtonAction(ActionEvent e)
    {
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex) {Thread.currentThread().interrupt();}

        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        textAreaInfo.append("Server stopping... \n");

        textAreaInfo.setText("");
    }

    private void activeUsersAction (ActionEvent e)
    {
        textAreaInfo.append("\n Online users : \n");
        for (String current_user : users)
        {
            textAreaInfo.append(current_user);
            textAreaInfo.append("\n");
        }
    }

    private void clearButtonAction(ActionEvent e)
    {
        textAreaInfo.setText("");
    }


    public class ClientHandler implements Runnable
    {
        BufferedReader reader;
        Socket sock;
        PrintWriter client;

        public ClientHandler(Socket clientSocket, PrintWriter user)
        {
            client = user;
            try
            {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex)
            {
                textAreaInfo.append("Unexpected error... \n");
            }

        }

        @Override
        public void run()
        {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
            String[] data;

            try
            {
                while ((message = reader.readLine()) != null)
                {
                    textAreaInfo.append("Received: " + message + "\n");
                    data = message.split(":");

                    for (String token:data)
                    {
                        textAreaInfo.append(token + "\n");
                    }

                    if (data[2].equals(connect))
                    {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);
                    }
                    else if (data[2].equals(disconnect))
                    {
                        tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        userRemove(data[0]);
                    }
                    else if (data[2].equals(chat))
                    {
                        tellEveryone(message);
                    }
                    else
                    {
                        textAreaInfo.append("No Conditions were met. \n");
                    }
                }
            }
            catch (Exception ex)
            {
                textAreaInfo.append("Lost a connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
            }
        }
    }

    public class ServerStart implements Runnable
    {
        @Override
        public void run()
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();

            try
            {
                ServerSocket serverSock = new ServerSocket(2222);

                while (true)
                {
                    Socket clientSock = serverSock.accept();
                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                    clientOutputStreams.add(writer);

                    Thread listener = new Thread(new ClientHandler(clientSock, writer));
                    listener.start();
                    textAreaInfo.append("Got a connection. \n");
                }
            }
            catch (Exception ex)
            {
                textAreaInfo.append("Error making a connection. \n");
            }
        }
    }

    public void userAdd (String data)
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        textAreaInfo.append("Before " + name + " added. \n");
        users.add(name);
        textAreaInfo.append("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList)
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void userRemove (String data)
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList)
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void tellEveryone(String message)
    {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext())
        {
            try
            {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                textAreaInfo.append("Sending: " + message + "\n");
                writer.flush();
                textAreaInfo.setCaretPosition(textAreaInfo.getDocument().getLength());

            }
            catch (Exception ex)
            {
                textAreaInfo.append("Error telling everyone. \n");
            }
        }
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }  catch(Exception e){
        }
        JFrame serverFrame =new JFrame ("Server");
        Server server=new Server();
        serverFrame.setContentPane(server.MainPanel);
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverFrame.pack();
        serverFrame.setVisible(true);
        serverFrame.setLocationRelativeTo(null);
    }
}
