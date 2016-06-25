import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.io.*;

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends Actor {
    OnClickListener cl;
    int width, height;
    String text;
    float fontSize;
    boolean pressed;
    boolean enabled = true;

    public Button(int width, int height, float fontSize, String text) {
        this.width = width;
        this.height = height;
        this.text = text;
        this.fontSize = fontSize;
        draw();
    }

    public void setOnClickListener(OnClickListener cl) {
        this.cl = cl;
    }

    public void act() {
        if(Greenfoot.mousePressed(this)) {
            System.out.println("Pressed");
            pressed = true;
            draw();
        }
        if(Greenfoot.mouseClicked(this)) {
            System.out.println("Clicked");
            pressed = false;
            draw();
        }
        if(Greenfoot.mouseClicked(this)) {
            if(enabled) {
                cl.onClick();
            }
        }
    }

    public void draw() {
        GreenfootImage sprite = new GreenfootImage(width, height);
        Graphics2D g = sprite.getAwtImage().createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try{
            g.setFont(Font.createFont(Font.PLAIN, getClass().getResourceAsStream("fonts/Roboto-Medium.ttf")).deriveFont(fontSize));
        } catch(FontFormatException|IOException e) {
            e.printStackTrace();
        }
        if(pressed && enabled) {
            g.setColor(new Color(150,150,150));
        } else {
            g.setColor(new Color(200,200,200));
        }
        g.fillRect(3, 3, width - 6, height - 6);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(5));
        g.drawRect(3, 3, width - 6, height - 6);
        if(!enabled) {
            g.setColor(new Color(120,120,120));
        }
        g.drawString(text, (width - g.getFontMetrics().stringWidth(text)) / 2, height * 0.75f);
        setImage(sprite);
    }

    public void disable() {
        enabled = false;
        draw();
    }
    
    public void enable() {
        enabled = true;
        draw();
    }
}
