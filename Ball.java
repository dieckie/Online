import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.awt.*;

/**
 * Write a description of class Ball here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ball extends Actor {

    final int SIZE = 20;
    final float SPEED = 2.5f;

    float vx, vy;
    float x, y;

    boolean init = true;
    boolean update = false;
    PongWorld world;

    public Ball() {
        GreenfootImage sprite = new GreenfootImage(SIZE, SIZE);
        sprite.setColor(Color.WHITE);
        sprite.fill();
        setImage(sprite);
    }

    public void act() {
        update = false;
        if(init) {
            init = false;
            world = (PongWorld) getWorld();
            resetLocation();
        }

        collisionDetection();
        movement();
        if(update && world.isServer()) {
            world.updateBall();
        }
    }
    
    public void collisionDetection() {
        Paddle p = (Paddle) getOneIntersectingObject(Paddle.class);
        if(p != null) {
            //Links
            float realY = y - (vy / vx) * (p.getX() + p.getWidth() / 2 - x);
            if(p.getX() > 640) {
                realY = y - (vy / vx) * (p.getX() - p.getWidth() / 2 - x);
            }
            float distance = y - p.getFY();
            if(Math.abs(distance) + 2 < (p.getHeight() + SIZE) / 2) {
                //float angle = distance * ((float)Math.PI * 0.5f / (p.getHeight() + SIZE) );
                //System.out.println(angle);
                //vx *= -1;
                //vy = distance / (p.getHeight() + SIZE) / 2 * SPEED * (float) Math.PI;
                vx *= -1;
                // vx = (float) Math.cos(angle) * -1 * SPEED;
                // vy = (float) Math.sin(angle) * SPEED;
                update = true;
                movement();
            } else {
                vy *= -1;
                // if(distance > 0) {
                // y = p.getFY() + ((p.getHeight() + SIZE) / 2 + 2);
                // if(vy < 0) {
                // vy *= -1;
                // }
                // } else {
                // y = p.getFY() - ((p.getHeight() + SIZE) / 2 + 2);
                // if(vy > 0) {
                // vy *= -1;
                // }
                // }
                update = true;
                movement();
            }

        }
        //Colliding with Bot and Top
        if(y - SIZE / 2 < 0) {
            vy *= -1;
            update = true;
        } else if(y + SIZE / 2 > getWorld().getHeight()) {
            vy *= -1;            
            update = true;
        }
        if(x <= 0) {
            resetLocation();
        } else if(x >= getWorld().getWidth()) {
            resetLocation();
        }
    }

    public void movement() {
        x += vx;
        y += vy;
        setLocation(Math.round(x), Math.round(y));
    }

    public void resetLocation() {
        x = 640;
        y = 360;
        Random r = new Random();
        do {
            float angle = r.nextInt(360) * (float)Math.PI / 180f;
            vx = (float) Math.cos(angle) * SPEED;
            vy = (float) Math.sin(angle) * SPEED;
        } while(vx < SPEED / 5);
        update = true;
    }

    public float getFX() {
        return x;
    }

    public float getFY() {
        return y;
    }

    public float getVX() {
        return vx;
    }

    public float getVY() {
        return vy;
    }

    public void setFLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }
}
