package mediator;

import entity.Player;
import main.CollisionChecker;
import main.GamePanel;
import main.GameUI;
import main.KeyHandler;
import tile.Map;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Mediator {

    public GamePanel gp;
    public BufferedImage fogOfWar;
    public Map currentMap;
    public KeyHandler keyH;
    public CollisionChecker cChecker;
    public Player player;
//    public int currentCols;
//    public int currentRows;
    GameUI gameUI;
    boolean gameUIOn = false;
    boolean toLevel2 = false;
    public Map[] maps;

    public Mediator() {
        gameUI = new GameUI(this);
        maps = new Map[3];
    }
    public void setGameUIOn(boolean gameUIOn) {
        this.gameUIOn = gameUIOn;
    }
    public boolean getGameUIOn() {
        return gameUIOn;
    }
    public GameUI getGameUI() { return gameUI; }
    public void draw(Graphics2D g2) { gameUI.draw(g2); };

    public void showMessage(String mes) {
        gameUI.showMessage(mes);
    }

    public void toLevel2(boolean to) {
        toLevel2 = to;
    }
    public boolean getToLevel2() {
        return toLevel2;
    }
    public void setPlayerMap(int mapNumber) {
        player.currentMap = maps[mapNumber-1];
    }
}
