package entity;

import mediator.Mediator;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public int x, y;
    public int width, height;
    public int speed = 2;
    public String name;
    public String direction = "down";
    public Rectangle solidArea;
    public boolean collisionOn = false;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Mediator mediator;
    public BufferedImage spriteSheet;

    public abstract void update();
    public abstract void draw(Graphics2D g2);
}
