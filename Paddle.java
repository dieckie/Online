import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;

/**
 * Write a description of class Paddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Paddle extends Actor {
    float vy;
    float y;

    final int HEIGHT = 100;
    final int WIDTH = 30;
    final float FRICTION = 0.98f;

    final float SPEED = 4f;

    boolean init = true;
    /** Spielt der Player von hier oder der ferne? */
    boolean isPlayer;

    long lastTime;
    public Paddle(boolean isPlayer) {
        this.isPlayer = isPlayer;
        GreenfootImage sprite = new GreenfootImage(WIDTH, HEIGHT);
        sprite.setColor(Color.WHITE);
        sprite.fill();
        setImage(sprite);
    }

    public void act() {
        if(init) {
            init = false;
            y = getY();
        }
        if(isPlayer) {
            int deltaTime = (int)(System.currentTimeMillis() - lastTime);
            float factor = deltaTime / 20f;
            lastTime = System.currentTimeMillis();
            controlls(factor);
        }
    }

    public void controlls(float factor) {
        if(Greenfoot.isKeyDown("W") || Greenfoot.isKeyDown("up")) {
            if(getY() > HEIGHT / 2) {
                y -= SPEED * factor;
            }
        }
        if(Greenfoot.isKeyDown("S") || Greenfoot.isKeyDown("down")) {
            if(getY() < getWorld().getHeight() - (HEIGHT / 2)) {
                y += SPEED * factor;
            }
        }
        setLocation(getX(), Math.round(y));
    }

    public float getFY() {
        return y;
    }

    public void setFY(float y) {
        this.y = y;
        setLocation(getX(), Math.round(y));
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}
