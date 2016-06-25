import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

/**
 * Write a description of class Connect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Connect extends World
{

    EditText ip, portJoin, portHost;
    Button host, join;
    ExecutorService executor;

    public Connect() {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1280, 720, 1);
        Greenfoot.setSpeed(90);
        setBackground("images/background_connect.png");
        placeGUI();
        Greenfoot.start();
        executor = Executors.newCachedThreadPool();
    }

    Runnable server = new Runnable() {
            public void run(){
                try {
                    ServerSocket server = new ServerSocket(Integer.parseInt(portHost.getText()));
                    Socket client = server.accept();
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    out.println(System.getProperty("user.name"));
                    Scanner in = new Scanner(client.getInputStream());
                    System.out.println("Server: " + in.nextLine());
                    server.close();
                    in.close();
                    client.close();
                    Greenfoot.setWorld(new PongWorld(server.getLocalPort(), null));
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        };

    Runnable client = new Runnable() {
            public void run() {
                try {
                    Socket s = new Socket(ip.getText(), Integer.parseInt(portJoin.getText()));
                    PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                    out.println(System.getProperty("user.name"));
                    Scanner in = new Scanner(s.getInputStream());
                    System.out.println("Client: " + in.nextLine());
                    s.close();
                    Greenfoot.setWorld(new PongWorld(s.getPort(), ip.getText()));
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        };

    public void placeGUI() {
        ip = new EditText(600, 30f, "IP");
        ip.setAllowed("0123456789abcdefABCDEF.:");
        addObject(ip, 320, 300);
        portJoin = new EditText(200, 30f, "Port");
        portJoin.setAllowed("0123456789");
        addObject(portJoin, 320, 400);
        join = new Button(200, 50, 30f, "Join");
        join.setOnClickListener(new OnClickListener(){

                public void onClick() {
                    executor.execute(client);
                    join.disable();
                    host.disable();
                }    

            });
        addObject(join, 320, 500);

        portHost = new EditText(200, 30f, "Port");
        portHost.setAllowed("0123456789");
        addObject(portHost, 960, 350);
        host = new Button(200, 50, 30f, "Host");
        host.setOnClickListener(new OnClickListener(){

                public void onClick() {
                    executor.execute(server);
                    join.disable();
                    host.disable();
                }    

            });
        addObject(host, 960, 450);
    }
}
