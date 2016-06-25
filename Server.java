import greenfoot.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
/**
 * Write a description of class Server here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Server {

    int port;
    PongWorld world;
    ServerSocket server;
    Socket client;
    ExecutorService executor;
    PrintWriter out;
    Scanner in;

    public Server(PongWorld world, int port) {
        this.port = port;
        this.world = world;
        try {
            server = new ServerSocket(port);
        } catch(IOException e) {
            e.printStackTrace();
        }
        executor = Executors.newCachedThreadPool();
        accept();
    }

    public void accept() {
        Runnable r = new Runnable() {
                @Override 
                public void run() {
                    try {
                        client = server.accept();
                        out = new PrintWriter(client.getOutputStream(), true);
                        out.println("Ready");
                        in = new Scanner(client.getInputStream());
                        String line = in.nextLine();
                        if(line.equals("Ready")) {
                            System.out.println("Serverside Ready");
                            start();
                            Greenfoot.start();
                        }
                    } catch(IOException e) {
                        e.printStackTrace();
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