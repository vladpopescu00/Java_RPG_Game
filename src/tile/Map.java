package tile;

import entity.*;
import main.GameConstants;
import main.GamePanel;
import mediator.Mediator;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class Map {

    public int rows;
    public int cols;
    public TileManager layer1;
    public TileManager layer2;
    public Player player;
    public GamePanel gp;

    public int spawnPointCol;
    public int spawnPointRow;

    public SuperObject[] objects;
    public Entity[] entities;
    public Mediator mediator;
    public boolean fogOfWarOn;

    public Map(GamePanel gp, Player pl, Mediator mediator, boolean fogOfWarOn, String filename1, String filename2, String gameData) {
        this.gp = gp;
        this.player = pl;
        this.mediator = mediator;
        this.fogOfWarOn = fogOfWarOn;

        try {

            InputStream is = getClass().getResourceAsStream(gameData);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String[] data = br.readLine().split(" +");
            rows = Integer.parseInt(data[0]);
            cols = Integer.parseInt(data[1]);

            int objNumber = Integer.parseInt(data[2]);
            objects = new SuperObject[objNumber];

            int enemyNumber = Integer.parseInt(data[3]);
            entities = new Entity[enemyNumber];

            data = br.readLine().split(" +");
            if(data[0].compareTo("spawn_point") == 0)
            {
                spawnPointCol = Integer.parseInt(data[1]);
                spawnPointRow = Integer.parseInt(data[2]);
            }

            for (int i = 0; i < objects.length; i++)
            {
                data = br.readLine().split(" +");
                switch(data[0]) {
                    case "spike":
                        objects[i] = new ObjSpike();
                        objects[i].x = Integer.parseInt(data[1]) * GameConstants.TILE_SIZE;
                        objects[i].y = Integer.parseInt(data[2]) * GameConstants.TILE_SIZE;
                        break;
                    case "stairs":
                        objects[i] = new ObjStairs();
                        objects[i].x = Integer.parseInt(data[1]) * GameConstants.TILE_SIZE;
                        objects[i].y = Integer.parseInt(data[2]) * GameConstants.TILE_SIZE;
                        break;
                    case "health_potion":
                        objects[i] = new ObjHealthPotion();
                        objects[i].x = Integer.parseInt(data[1]) * GameConstants.TILE_SIZE;
                        objects[i].y = Integer.parseInt(data[2]) * GameConstants.TILE_SIZE;
                        break;
                    case "time_potion":
                        objects[i] = new ObjTimePotion();
                        objects[i].x = Integer.parseInt(data[1]) * GameConstants.TILE_SIZE;
                        objects[i].y = Integer.parseInt(data[2]) * GameConstants.TILE_SIZE;
                        break;
                    case "acid_potion":
                        objects[i] = new ObjAcidPotion();
                        objects[i].x = Integer.parseInt(data[1]) * GameConstants.TILE_SIZE;
                        objects[i].y = Integer.parseInt(data[2]) * GameConstants.TILE_SIZE;
                        break;
                    case "coins":
                        objects[i] = new ObjCoins();
                        objects[i].x = Integer.parseInt(data[1]) * GameConstants.TILE_SIZE;
                        objects[i].y = Integer.parseInt(data[2]) * GameConstants.TILE_SIZE;
                        break;
                    case "blue_rock":
                        objects[i] = new ObjBlueRock();
                        objects[i].x = Integer.parseInt(data[1]) * GameConstants.TILE_SIZE;
                        objects[i].y = Integer.parseInt(data[2]) * GameConstants.TILE_SIZE;
                        break;
                    case "green_rock":
                        objects[i] = new ObjGreenRock();
                        objects[i].x = Integer.parseInt(data[1]) * GameConstants.TILE_SIZE;
                        objects[i].y = Integer.parseInt(data[2]) * GameConstants.TILE_SIZE;
                        break;
                    default:
                        break;
                }
            }

            for (int i = 0; i < entities.length; i++)
            {
                data = br.readLine().split(" +");
                switch(data[0]) {
                    case "goblin":
                        entities[i] = new Goblin(mediator, Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                        break;
                    case "skeleton":
                        entities[i] = new Skeleton(mediator, Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                        break;
                    case "tiki":
                        entities[i] = new Tiki(mediator, Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                        break;
                    default:
                        break;
                }
            }

            br.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        GameConstants.MAX_SCREEN_COL = cols;
        GameConstants.MAX_SCREEN_ROW = rows;

        this.layer1 = new TileManager(gp, mediator, filename1, rows, cols);
        this.layer2 = new TileManager(gp, mediator, filename2, rows, cols);
    }

    public void draw(Graphics2D g2) {

        layer1.draw(g2);

        for (int i = 0; i < objects.length; i++)
        {
            if(objects[i] != null)
            {
                objects[i].draw(g2);
            }
        }

        for (int i = 0; i < entities.length; i++) {
            if (entities[i] != null) {
                entities[i].draw(g2);
            }
        }

        player.draw(g2);

        layer2.draw(g2);

        if(fogOfWarOn) {
            g2.drawImage(mediator.fogOfWar, player.x - (200-32)/2, player.y - (200-36)/2, 200, 200, null);

            int viewHeight = 200;
            int viewWidth = 200;
            int viewX = player.x - (viewWidth - player.width)/2;
            int viewY = player.y - (viewHeight - player.height)/2;

            g2.fillRect(0, 0, viewX, GameConstants.SCREEN_HEIGHT); // left
            g2.fillRect(viewX, 0, viewWidth, viewY); // up
            g2.fillRect(viewX + viewWidth, 0, GameConstants.SCREEN_WIDTH - (viewX + viewWidth), GameConstants.SCREEN_HEIGHT); // right
            g2.fillRect(viewX, viewY + viewHeight, viewWidth, GameConstants.SCREEN_HEIGHT - (viewY + viewHeight)); // downs
        }

    }

    public void update() {
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] != null) {
                entities[i].update();
            }
        }
    }

}
