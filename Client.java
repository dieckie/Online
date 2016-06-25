import greenfoot.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
/**
 * Write a description of class Client here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Client {
    int port;
    String ip;
    PongWorld world;
    Socket server;
    ExecutorService executor;
    PrintWriter out;
    Scanner in;

    public Client(PongWorld world, int port, String ip) {
        this.port = port;
        this.ip = ip;
        this.world = world;
        executor = Executors.newCachedThreadPool();
        accept();
    }

    public void accept() {
        Runnable r = new Runnable() {
                @Override 
                public void run() {
                    for(int i = 0; i < 10; i++) {
                        try {
                            server = new Socket(ip, port);
                            out = new PrintWriter(server.getOutputStream(), true);
                            out.println("Ready");
                            in = new Scanner(server.getInputStream());
                            String line = in.nextLine();
                            if(line.equals("Ready")) {
                                System.out.println("Clientside Ready");
                                start();
                                Greenfoot.start();
                                break;
                            }
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        executor.execute(r);
    }

    public void start() {
        Runnable r = new Runnable() {
                @Override 
                public void run() {
                    while(true) {
                        if(in.hasNextLine()) {
                            String line = in.nextLine();
                            world.recieve(line);
                            //System.out.println(line);
                        }
                    }
                }
            };
        executor.execute(r);
    }

    public void send(String message) {
        out.println(message);
    }

}
