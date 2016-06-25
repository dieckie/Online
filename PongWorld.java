import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PongWorld extends World {

    Paddle p1, p2;
    Ball b;
    boolean running = false;
    boolean isServer;
    Server server = null;
    Client client = null;

    public PongWorld(int port, String ip) {
        super(1280, 720, 1);
        setBackground("images/background.png");
        prepare();
        Greenfoot.setSpeed(50);
        isServer = ip == null;
        if(ip == null) {
            server = new Server(this, port);
        } else {
            client = new Client(this, port, ip);
        }
        //Greenfoot.start();
    }

    public void prepare() {
        p1 = new Paddle(true);
        addObject(p1, 40, 360);
        p2 = new Paddle(false);
        addObject(p2, 1240, 360);
        b = new Ball();
        addObject(b, 0, 0);
    }

    public void act() {
        if(isServer) {
            server.send(p1.getFY() + "");
        } else {
            client.send(p1.getFY() + "");
        }
    }

    public void updateBall() {
        server.send(b.getFX() + " " + b.getFY() + " " + b.getVX() + " " + b.getVY());
    }

    public void recieve(String message) {
        if(message.contains(" ")) {
            String[] s = message.split(" ");
            b.setFLocation(getWidth() - Float.parseFloat(s[0]), Float.parseFloat(s[1]));
            b.setVelocity(Float.parseFloat(s[2]) * -1, Float.parseFloat(s[3]));
        } else {
            p2.setFY(Float.parseFloat(message));
        }
    }

    public Paddle getPlayer(int player) {
        if(player == 1) {
            return p1;
        } else if(player == 2) {
            return p2;
        }
        return null;
    }

    public boolean isServer() {
        return isServer;
    }

    public boolean isRunning() {
        return running;
    }

    public void pause() {
        running = false;
    }

    public void start() {
        running = true;
    }

}
