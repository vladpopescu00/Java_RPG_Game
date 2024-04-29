package tile;

import main.GamePanel;
import main.GameConstants;
import mediator.Mediator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    public static final int GROUND = 0;
    public static final int WALL = 1;
    public static final int INVISIBLE = 2;
    public static final int BRICKS_UP = 3;
    public static final int BRICKS_DOWN = 4;
    public static final int BRICKS_RIGHT = 5;
    public static final int BRICKS_LEFT = 6;
    public static final int BRICKS_UPPER_RIGHT = 7;
    public static final int BRICKS_UPPER_LEFT = 8;
    public static final int BRICKS_DOWN_RIGHT = 9;
    public static final int BRICKS_DOWN_LEFT = 10;
    public static final int BRICKS_CONNECT_RIGHT_DOWN = 11;
    public static final int BRICKS_CONNECT_RIGHT_UPPER = 12;
    public static final int BRICKS_CONNECT_LEFT_DOWN = 13;
    public static final int BRICKS_CONNECT_LEFT_UPPER = 14;



    int rows;
    int cols;

    GamePanel gp;
    Mediator mediator;
    public Tile[] tiles;
    BufferedImage spriteSheet;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp, Mediator mediator, String fileName, int rows, int cols) {
        this.gp = gp;
        this.mediator = mediator;
        this.rows = rows;
        this.cols = cols;
        tiles = new Tile[20];
        getTileImages();
        mapTileNum = new int[cols][rows];
        loadMap(fileName);
    }


    public void getTileImages() {
        try {
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/tilemap.png"));

            tiles[GROUND] = new Tile();
            tiles[WALL] = new Tile();
            tiles[INVISIBLE] = new Tile();
            tiles[BRICKS_UP] = new Tile();
            tiles[BRICKS_RIGHT] = new Tile();
            tiles[BRICKS_LEFT] = new Tile();
            tiles[BRICKS_DOWN] = new Tile();
            tiles[BRICKS_DOWN_RIGHT] = new Tile();
            tiles[BRICKS_UPPER_LEFT] = new Tile();
            tiles[BRICKS_DOWN_LEFT] = new Tile();
            tiles[BRICKS_UPPER_RIGHT] = new Tile();

            tiles[BRICKS_CONNECT_RIGHT_DOWN] = new Tile();
            tiles[BRICKS_CONNECT_RIGHT_UPPER] = new Tile();
            tiles[BRICKS_CONNECT_LEFT_DOWN] = new Tile();
            tiles[BRICKS_CONNECT_LEFT_UPPER] = new Tile();

            /////////////////////////////////////////////////////////////////////////////////////

            tiles[GROUND].image = spriteSheet.getSubimage(2*16, 0*16, 16, 16);
            tiles[INVISIBLE].image = spriteSheet.getSubimage(0*16, 0*16, 16, 16);
            tiles[WALL].image = spriteSheet.getSubimage(6*16, 4*16, 16, 16);

            tiles[BRICKS_UP].image = spriteSheet.getSubimage(2*16, 5*16, 16, 16);
            tiles[BRICKS_DOWN].image = spriteSheet.getSubimage(2*16, 6*16, 16, 16);
            tiles[BRICKS_RIGHT].image = spriteSheet.getSubimage(4*16, 4*16, 16, 16);
            tiles[BRICKS_LEFT].image = spriteSheet.getSubimage(3*16, 4*16, 16, 16);
            tiles[BRICKS_UPPER_RIGHT].image = spriteSheet.getSubimage(5*16, 5*16, 16, 16);
            tiles[BRICKS_UPPER_LEFT].image = spriteSheet.getSubimage(1*16, 5*16,16, 16);
            tiles[BRICKS_DOWN_LEFT].image = spriteSheet.getSubimage(1*16, 6*16,16, 16);
            tiles[BRICKS_DOWN_RIGHT].image = spriteSheet.getSubimage(5*16, 6*16,16, 16);
            tiles[BRICKS_CONNECT_RIGHT_DOWN].image = spriteSheet.getSubimage(4*16, 5*16,16, 16);
            tiles[BRICKS_CONNECT_RIGHT_UPPER].image = spriteSheet.getSubimage(4*16, 3*16,16, 16);
            tiles[BRICKS_CONNECT_LEFT_DOWN].image = spriteSheet.getSubimage(3*16, 5*16,16, 16);
            tiles[BRICKS_CONNECT_LEFT_UPPER].image = spriteSheet.getSubimage(3*16, 3*16,16, 16);


            tiles[WALL].collision = true;
            tiles[INVISIBLE].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String fileName) {
        try {
            InputStream is = getClass().getResourceAsStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for(int i = 0; i < rows; i++)
            {
                String line = br.readLine();
                String[] numbers = line.split(" +");

                for (int j = 0; j < cols; j++)
                {
                    mapTileNum[j][i] = Integer.parseInt(numbers[j]);
                }
            }

            br.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;

        while (col < cols && row < rows)
        {
            int tileNum = mapTileNum[col][row];
            if (tileNum != -1) {
                g2.drawImage(tiles[tileNum].image, col*GameConstants.TILE_SIZE, row*GameConstants.TILE_SIZE,
                        GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);
            }

            col++;
            if(col == cols)
            {
                col = 0;
                row++;
            }
        }
    }
}
