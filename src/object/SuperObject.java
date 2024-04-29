package object;

import main.GameConstants;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;



public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int x, y;
    private static final int HEIGHT = 32;
    private static final int WIDTH = 32;
    public Rectangle solidArea = new Rectangle(0, 0, HEIGHT, WIDTH);
    public int solidAreaDefaultX = 0, solidAreaDefaultY = 0;

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);
    }
}
