package main;

import entity.Player;
import mediator.Mediator;
import object.SuperObject;
import states.*;
import tile.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private static GamePanel instance;

    public Thread gameThread;

    public Mediator mediator = new Mediator();
    public KeyHandler keyH = new KeyHandler();
    public Player player = new Player(this, keyH, mediator);

    public int state = State.MENU;
    public MenuState menu;
    public Map01State map01playing;
    public Map02State map02playing;
    public Map03State map03playing;
    public InGame inGame;


    public static GamePanel getInstance() {
        if(instance == null) {
            instance = new GamePanel();
        }
        return instance;
    }

    private GamePanel() {
        this.setPreferredSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        init();
    }

    public void init() {

        try {
            mediator.fogOfWar = ImageIO.read(getClass().getResourceAsStream("/fog_of_war.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediator.maps[0] = new Map(this, player, mediator, false,"/map01.txt", "/map01_2.txt", "/gamedata.txt");;
        mediator.maps[1] = new Map(this, player, mediator, false,"/map02.txt", "/map02_2.txt", "/game_data_map02.txt");;
        mediator.maps[2] = new Map(this, player, mediator, false,"/map03.txt", "/map03_2.txt", "/game_data_map03.txt");;
        mediator.currentMap = mediator.maps[0];
        mediator.keyH = keyH;
        mediator.cChecker = new CollisionChecker(this, mediator.maps[0]);
        mediator.player = player;
        mediator.gp = this;

        player.currentMap = mediator.currentMap;

        menu = new MenuState(this, mediator);
        inGame = new InGame(this, mediator);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/GameConstants.FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();

            if(gameThread != null) {
                repaint();
            }

            try
            {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if(remainingTime < 0) { remainingTime = 0; }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        switch (state) {
            case State.MENU:
                menu.update();
                break;
            case State.INGAME:
                inGame.update();
                break;
            default:
                break;
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        switch (state) {
            case State.MENU:
                menu.draw(g2);
                break;
            case State.INGAME:
                inGame.draw(g2);
                break;
            default:
                break;
        }

        g2.dispose();
    }
}
