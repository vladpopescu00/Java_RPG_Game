package entity;

import main.GameConstants;
import main.GamePanel;
import main.KeyHandler;
import mediator.Mediator;
import tile.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    public BufferedImage up1, up2, up3,
            down1, down2, down3,
            left1, left2, left3,
            right1, right2, right3;

    BufferedImage spriteSheet;
    public Map currentMap;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Player's abilities
    public int health = 3;
    public int invincibility = 120;
    public int money = 0;
    public int acid_potions = 0, time_potions = 0;

    public Player(GamePanel gp, KeyHandler keyH, Mediator mediator) {
        this.gp = gp;
        this.keyH = keyH;
        this.mediator = mediator;
        this.currentMap = mediator.currentMap; // gp.map1 - old value

        //setDefaultValues();
        getPlayerImage();
        solidArea = new Rectangle(4, 22, 24, 14);
        solidAreaDefaultX = 4;
        solidAreaDefaultY = 22;
    }
    public void setDefaultValues() {
        x = currentMap.spawnPointCol * GameConstants.TILE_SIZE;
        y = currentMap.spawnPointRow * GameConstants.TILE_SIZE;
        speed = 2;
        direction = "down";
        width = 2*16;
        height = 2*18;
    }

    public void getPlayerImage() {
        try {
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/player.png"));
            up1 = spriteSheet.getSubimage(0*16, 0*18, 16, 18);
            up2 = spriteSheet.getSubimage(1*16, 0*18, 16, 18);
            up3 = spriteSheet.getSubimage(2*16, 0*18, 16, 18);
            down1 = spriteSheet.getSubimage(0*16, 2*18, 16, 18);
            down2 = spriteSheet.getSubimage(1*16, 2*18, 16, 18);
            down3 = spriteSheet.getSubimage(2*16, 2*18, 16, 18);
            left1 = spriteSheet.getSubimage(0*16, 3*18, 16, 18);
            left2 = spriteSheet.getSubimage(1*16, 3*18, 16, 18);
            left3 = spriteSheet.getSubimage(2*16, 3*18, 16, 18);
            right1 = spriteSheet.getSubimage(0*16, 1*18, 16, 18);
            right2 = spriteSheet.getSubimage(1*16, 1*18, 16, 18);
            right3 = spriteSheet.getSubimage(2*16, 1*18, 16, 18);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        direction = "down";
        boolean pressedKey = false;

        if(health == 0){
            x = currentMap.spawnPointCol * GameConstants.TILE_SIZE;
            y = currentMap.spawnPointRow * GameConstants.TILE_SIZE;
            health = 3;
            invincibility = 120;
            direction = "down";
            System.out.println("YOU DIED");
        }

        if(keyH.upPressed == true)
        {
            pressedKey = true;
            direction = "up";
        }
        if(keyH.downPressed == true)
        {
            pressedKey = true;
            direction = "down";
        }
        if(keyH.leftPressed == true)
        {
            pressedKey = true;
            direction = "left";
        }
        if(keyH.rightPressed == true)
        {
            pressedKey = true;
            direction = "right";
        }

        collisionOn = false;
        mediator.cChecker.checkTile(this);

        int objIndex = mediator.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        int enemyIndex = mediator.cChecker.checkEntity(this, true);
        interactEnemy(enemyIndex);

        if (invincibility < 120 && invincibility > 0){
            invincibility--;
        }
        else {
            invincibility = 120;
        }

        if(collisionOn == false && pressedKey)
        {
            if(keyH.upPressed == true) {
                y -= speed;
            }
            if(keyH.downPressed == true) {
                y += speed;
            }
            if(keyH.leftPressed == true) {
                x -= speed;
            }
            if(keyH.rightPressed == true) {
                x += speed;
            }
//            switch (direction)
//            {
//                case "up":
//
//                    break;
//                case "down":
//
//                    break;
//                case "left":
//                    break;
//                case "right":
//                    break;
//            }
        }

        if(pressedKey)
        {
            spriteCounter++;
            if(spriteCounter > 10)
            {
                if(spriteNum == 1)
                    spriteNum = 2;
                else if(spriteNum == 2)
                    spriteNum = 3;
                else if(spriteNum == 3)
                    spriteNum = 1;
                spriteCounter = 0;
            }
        }
        else
        {
            spriteCounter = 0;
            spriteNum = 1;
        }
    }

    public void pickUpObject(int index) {
        if(index != 999){
            String objName = currentMap.objects[index].name;

            switch (objName) {
                case "health potion":
                    health++;
                    System.out.println("Current health = " + health);
                    mediator.showMessage("Picked up a health potion!");
                    currentMap.objects[index] = null;
                    break;
                case "spike":
                    if(invincibility == 120) {
                        health--;
                        invincibility--;
                        System.out.println("Current health = " + health);
                        mediator.showMessage("You lost a life!");
                    }
                    break;
                case "stairs":
                    System.out.println("To the next level!");
                    mediator.showMessage("To the next level!");
                    //mediator.toLevel2(true);
                    x = currentMap.spawnPointCol * GameConstants.TILE_SIZE;
                    y = currentMap.spawnPointRow * GameConstants.TILE_SIZE;
                    break;
                case "time potion":
                    System.out.println("Picked up a time potion!");
                    mediator.showMessage("Picked up a time potion!");
                    time_potions++;
                    currentMap.objects[index] = null;
                    break;
                case "acid potion":
                    mediator.showMessage("Picked up an acid potion!");
                    acid_potions++;
                    System.out.println("Picked up an acid potion!");
                    currentMap.objects[index] = null;
                    break;
                case "coins":
                    mediator.showMessage("Picked up 20 coins!");
                    System.out.println("Picked up 20 coins!");
                    money += 20;
                    System.out.println("Money = " + money);
                    currentMap.objects[index] = null;
                    break;
                case "blue rock":
                    mediator.showMessage("Picked up a blue rock!");
                    System.out.println("Picked up a blue rock!");
                    money += 50;
                    System.out.println("Money = " + money);
                    currentMap.objects[index] = null;
                    break;
                case "green rock":
                    mediator.showMessage("Picked up a green rock!");
                    System.out.println("Picked up a green rock!");
                    money += 200;
                    System.out.println("Money = " + money);
                    currentMap.objects[index] = null;
                    break;
            }
        }
    }

    private void interactEnemy(int enemyIndex) {
        if (enemyIndex != 999) {
            String enemyName = currentMap.entities[enemyIndex].name;

            switch (enemyName) {
                case "goblin", "skeleton", "tiki":
                    if(invincibility == 120) {
                        health--;
                        invincibility--;
                        System.out.println("Current health = " + health);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
    //    g2.setColor(Color.BLUE);
    //    g2.fillRect(x, y, gp.tileSize, gp.tileSize);

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
