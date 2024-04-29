package main;

import mediator.Mediator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameUI {

    UIElement[] elements;
    Mediator mediator;
    BufferedImage health, money, acid_potion, time_potion;
    final Font textFont = new Font("Arial Bold", Font.PLAIN, 18);
    final Font messageFont = new Font("Times New Roman", Font.PLAIN, 20);
    boolean messageOn = false;
    String actualMessage;
    int timerMessage = 120;

    public GameUI(Mediator mediator) {
        this.mediator = mediator;
        try {
            BufferedImage temp_potions = ImageIO.read(getClass().getResourceAsStream("/potions.png"));
            health = temp_potions.getSubimage(0, 0, 32, 32);
            acid_potion = temp_potions.getSubimage(64, 0, 32, 32);
            time_potion = temp_potions.getSubimage(32, 0, 32, 32);
            money = ImageIO.read(getClass().getResourceAsStream("/riches.png")).getSubimage(0, 0, 32, 32);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String mes) {
        messageOn = true;
        actualMessage = mes;
        timerMessage = 120;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(health, 20, 20, 32, 32, null);
        g2.setColor(Color.ORANGE);
        g2.setFont(textFont);
        g2.drawString("x " + mediator.player.health, 20+32, 48);

        g2.drawImage(time_potion, 20+32+32, 20, 32, 32, null);
        g2.drawString("x " + mediator.player.time_potions, 20+32+32+32, 48);

        g2.drawImage(acid_potion, 20+32+32+32+32, 20, 32, 32, null);
        g2.drawString("x " + mediator.player.acid_potions, 20+32+32+32+32+32, 48);

        g2.drawImage(money, 20, 60, 32, 32, null);
        g2.drawString("x " + mediator.player.money, 20+32, 80);


        // Logica afisarii mesajelor legate de player pe ecran
        if(messageOn) {
            if(timerMessage == 0) {
                timerMessage = 120;
                messageOn = false;
            } else {
                g2.setColor(Color.WHITE);
                g2.setFont(messageFont);

                timerMessage--;
                // Extract width in pixels of actual message
                int messageWidth = (int)g2.getFontMetrics().getStringBounds(actualMessage, g2).getWidth();
                g2.drawString(actualMessage, mediator.player.x - messageWidth/2 + mediator.player.width/2, mediator.player.y - 5);

                g2.setFont(textFont);
                g2.setColor(Color.ORANGE);
            }
        }
    }

    public void update() {

    }
}

class UIElement {
    BufferedImage image;
    String text;
    GameUI gameUI;
    int posX, posY;

    protected UIElement(GameUI gameUI, BufferedImage img, String text) {
        this.gameUI = gameUI;
        this.image = img;
        this.text = text;
    }
}
