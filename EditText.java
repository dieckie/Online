import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * Write a description of class EditText here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EditText extends Actor {
    final int MARGIN_HOR = 4;

    StringBuilder text;
    int cursorPos = 0;
    float fontSize;
    int width;
    String hint = "";
    boolean inFocus = false;
    GreenfootImage img;
    boolean filter = false;
    String allowedChars = "";

    public EditText(int width, float fontSize, String hint) {
        this.fontSize = fontSize;
        this.hint = hint;
        this.width = width;
        text = new StringBuilder();
        prepareSprite();
        draw();

    }

    public void setAllowed(String allowed) {
        allowedChars = allowed;
        filter = true;
    }

    public void prepareSprite() {
        GreenfootImage sprite = new GreenfootImage(width, (int)(fontSize * 1.3f));
        Graphics2D g = sprite.getAwtImage().createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try{
            g.setFont(Font.createFont(Font.PLAIN, getClass().getResourceAsStream("fonts/Roboto-Medium.ttf")).deriveFont(fontSize));
        } catch(FontFormatException|IOException e) {
            e.printStackTrace();
        }
        g.setColor(new Color(0,0,0,20));
        g.fillRect(0,0,width, (int)(fontSize * 1.3f)); 
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(fontSize * 0.1f));
        g.drawLine(2, (int)(fontSize * 1.2f), width - 2, (int)(fontSize * 1.2f));
        img = sprite;
        setImage(sprite);
    }

    public void act() {
        checkMouse();
        if(inFocus) {
            checkKeys();
        }
    }

    public void draw() {
        GreenfootImage sprite = new GreenfootImage(img);
        Graphics2D g = sprite.getAwtImage().createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        try{
            g.setFont(Font.createFont(Font.PLAIN, getClass().getResourceAsStream("fonts/Roboto-Medium.ttf")).deriveFont(fontSize));
        } catch(FontFormatException|IOException e) {
            e.printStackTrace();
        }
        g.setColor(Color.BLACK);
        if(text.length() > 0) {
            g.drawString(text.toString(), MARGIN_HOR, (int)(fontSize * 0.9f));
        } else {
            if(!inFocus) {
                g.setColor(new Color(150,150,150));
                g.drawString(hint, MARGIN_HOR, (int)(fontSize * 0.9f));
                g.setColor(Color.BLACK);                
            }
        }
        if(inFocus) {
            String before = text.substring(0, cursorPos);
            int x = g.getFontMetrics().stringWidth(before) + MARGIN_HOR;
            g.setStroke(new BasicStroke(fontSize * 0.05f));
            g.drawLine(x, (int)(fontSize * 0.10f), x, (int)(fontSize * 1.10f));
        }
        setImage(sprite);
    }

    public void checkMouse() {
        greenfoot.MouseInfo m = Greenfoot.getMouseInfo();
        if(m != null) {
            if(m.getButton() != 0) {
                inFocus = (m.getX() < getX() + (width / 2) && m.getX() > getX() - (width / 2) &&  m.getY() < getY() + (int)(fontSize * 0.65f) && m.getY() > getY() - (int)(fontSize * 0.65f));
                draw();
            }
        }
    }

    public void checkKeys() {
        String key = Greenfoot.getKey();
        if(key != null && !key.equals("null")) {
            if(special(key)) {

            } else if(allowed(key)) {
                if(key.equals("space")) {
                    key = " ";
                }
                text.insert(cursorPos, key.charAt(0));
                cursorPos++;
            }
            draw();
        }
    }

    public boolean special(String key) {
        if(key.equals("backspace")) {
            if(cursorPos > 0) {
                text.deleteCharAt(cursorPos - 1);
                cursorPos--;
            }
            return true;
        } else if(key.equals("left")) {
            if(cursorPos > 0) {
                cursorPos--;
            }
            return true;
        } else if(key.equals("right")) {
            if(cursorPos < text.length()) {
                cursorPos++;
            }
            return true;
        }
        if(key.length() > 1) {
            System.out.println("EditText: KEY FOUND! " + key);
            if(key.equals("space")) {
                return false;
            }
            return true;
        }
        return false;

    }

    public boolean allowed(String key) {
        if(filter) {
            return allowedChars.contains(key);
        }
        return true;
    }
    
    public String getText() {
        return text.toString();
    }
}
