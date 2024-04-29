package entity;

import main.GameConstants;
import mediator.Mediator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Tiki extends Entity{

    int x1, y1;
    int spriteNum = 1, spriteCounter = 10;
    int speed = super.speed;
    public int changeDir = 180;
    public BufferedImage left1, left2, left3, left4,
            right1, right2, right3, right4;

    public Tiki(Mediator mediator, int x1, int y1) {
        this.mediator = mediator;
        this.width = 2*32;
        this.height = 2*16;
        this.solidArea = new Rectangle(0, 0, 64, 32);
        this.x = x1 * GameConstants.TILE_SIZE;
        this.y = y1 * GameConstants.TILE_SIZE;
        this.name = "skeleton";
        this.x1 = x1;
        this.y1 = y1;
        Random rand = new Random();
        this.changeDir = rand.nextInt(100)+40;
        getImage();
    }

    public void getImage() {
        try {
            this.spriteSheet = ImageIO.read(getClass().getResourceAsStream("/tiki.png"));
            this.right1 = spriteSheet.getSubimage(0 * 32, 0 * 16, 32, 16);
            this.right2 = spriteSheet.getSubimage(1 * 32, 0 * 16, 32, 16);
            this.right3 = spriteSheet.getSubimage(2 * 32, 0 * 16, 32, 16);
            this.right4 = spriteSheet.getSubimage(3 * 32, 0 * 16, 32, 16);
            this.left1 = spriteSheet.getSubimage(0 * 32, 1 * 16, 32, 16);
            this.left2 = spriteSheet.getSubimage(1 * 32, 1 * 16, 32, 16);
            this.left3 = spriteSheet.getSubimage(2 * 32, 1 * 16, 32, 16);
            this.left4 = spriteSheet.getSubimage(3 * 32, 1 * 16, 32, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        Random rand = new Random();
        int i = rand.nextInt(4);

        if(changeDir == 0) {
            switch (i) {
                case 0:
                    direction = "up";
                    break;
                case 1:
                    direction = "down";
                    break;
                case 2:
                    direction = "right";
                    break;
                case 3:
                    direction = "left";
                    break;
                default:
                    break;
            }
            changeDir = 180;
        } else {
            changeDir--;
        }

        if(spriteCounter == 0) {
            if(spriteNum == 1)
                spriteNum = 2;
            else if(spriteNum == 2)
                spriteNum = 3;
            else if(spriteNum == 3)
                spriteNum = 4;
            else if(spriteNum == 4)
                spriteNum = 1;
            spriteCounter = 10;
        } else {
            spriteCounter--;
        }

        collisionOn = false;
        mediator.cChecker.checkTile(this);

        if(collisionOn == false) {
            switch (direction) {
                case "up":
                    y -= speed;
                    break;
                case "down":
                    y += speed;
                    break;
                case "right":
                    x += speed;
                    break;
                case "left":
                    x -= speed;
                    break;
                default:
                    break;
            }
        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up", "left":
                if(spriteNum == 1)
                {
                    image = left1;
                }
                else if(spriteNum == 2)
                {
                    image = left2;
                }
                else if(spriteNum == 3)
                {
                    image = left3;
                }
                else if(spriteNum == 4)
                {
                    image = left4;
                }
                break;
            case "down", "right":
                if(spriteNum == 1)
                {
                    image = right1;
                }
                else if(spriteNum == 2)
                {
                    image = right2;
                }
                else if(spriteNum == 3)
                {
                    image = right3;
                }
                else if(spriteNum == 4)
                {
                    image = right4;
                }
                break;
        }
        g2.drawImage(image, x, y, width, height, null);
    }
}
