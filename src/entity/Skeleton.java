package entity;

import main.GameConstants;
import mediator.Mediator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Skeleton extends Entity{

    int x1, y1;
    int spriteNum = 1, spriteCounter = 10;
    int speed = super.speed;
    public int changeDir = 120;
    public BufferedImage down1, down2, down3, up1, up2, up3,
            right1, right2, right3, left1, left2, left3;

    public Skeleton(Mediator mediator, int x1, int y1) {
        this.mediator = mediator;
        this.width = 2*16;
        this.height = 2*18;
        this.solidArea = new Rectangle(0, 0, 32, 36);
        this.x = x1 * GameConstants.TILE_SIZE;
        this.y = y1 * GameConstants.TILE_SIZE;
        this.name = "skeleton";
        this.x1 = x1;
        this.y1 = y1;
        getImage();
    }

    public void getImage() {
        try {
            this.spriteSheet = ImageIO.read(getClass().getResourceAsStream("/skeleton.png"));
            this.down1 = spriteSheet.getSubimage(0*16, 0*18, 16, 18);
            this.down2 = spriteSheet.getSubimage(1*16, 0*18, 16, 18);
            this.down3 = spriteSheet.getSubimage(2*16, 0*18, 16, 18);
            this.up1 = spriteSheet.getSubimage(0*16, 1*18, 16, 18);
            this.up2 = spriteSheet.getSubimage(1*16, 1*18, 16, 18);
            this.up3 = spriteSheet.getSubimage(2*16, 1*18, 16, 18);
            this.right1 = spriteSheet.getSubimage(0*16, 2*18, 16, 18);
            this.right2 = spriteSheet.getSubimage(1*16, 2*18, 16, 18);
            this.right3 = spriteSheet.getSubimage(2*16, 2*18, 16, 18);
            this.left1 = spriteSheet.getSubimage(0*16, 3*18, 16, 18);
            this.left2 = spriteSheet.getSubimage(1*16, 3*18, 16, 18);
            this.left3 = spriteSheet.getSubimage(2*16, 3*18, 16, 18);
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
            case "up":
                if(spriteNum == 1)
                {
                    image = up1;
                }
                else if(spriteNum == 2)
                {
                    image = up2;
                }
                else if(spriteNum == 3)
                {
                    image = up3;
                }
                break;
            case "down":
                if(spriteNum == 1)
                {
                    image = down1;
                }
                else if(spriteNum == 2)
                {
                    image = down2;
                }
                else if(spriteNum == 3)
                {
                    image = down3;
                }
                break;
            case "left":
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
                break;
            case "right":
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
                break;
        }
        g2.drawImage(image, x, y, width, height, null);
    }
}
