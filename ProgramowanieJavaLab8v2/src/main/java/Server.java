import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    private int port ;
    private String name;
    Controller controller;
    public Server(String name,int port,Controller controller)
    {
        this.port=port;
        this.name=name;
        this.controller=controller;
        Thread thread = new Thread(){
            public void run(){

                ServerSocket listener = null;
                try {

                    try {
                        listener = new ServerSocket(port);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    while (true) {
                        try {
                            new Handler(listener.accept(),controller).start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    try {
                        listener.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

    }
}
class Handler extends Thread {
    private String name;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    Controller controller;
    public Handler(Socket socket,Controller controller) {
        this.socket = socket;
        this.controller=controller;
    }
    public void run() {
        try {

            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                String input = in.readLine();
                controller.getLayerConnector().sendMsg(input);
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }
}