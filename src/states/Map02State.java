package states;

import main.GameConstants;
import main.GamePanel;
import mediator.Mediator;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Map02State implements State {
    GamePanel gp;
    Mediator mediator;

    public Map02State(GamePanel gp, Mediator mediator) {
        this.gp = gp;
        this.mediator = mediator;
    }

    public void SalveazaJoc() {
        Connection c = null;
        Statement s = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:The_Lust_for_Gold.db");
            s = c.createStatement();

            ResultSet rs = s.executeQuery("SELECT * FROM GameData");
            while(rs.next())
            {
                int id = rs.getInt("ID");
                int map = rs.getInt("MAP");
                int posx = rs.getInt("POSITIONX");
                int posy = rs.getInt("POSITIONY");
                int health = rs.getInt("HEALTH");
                int money = rs.getInt("MONEY");
                System.out.println("ID = " + id + " Map = " + map + " Position x = " + posx + " Position y = " + posy + " Health = " + health + " Money = " + money);
            }

            String sql = "UPDATE GameData SET MAP = " + 2 + ", POSITIONX = " + gp.player.x
                    + ", POSITIONY = " + gp.player.y + ", HEALTH = " + gp.player.health
                    + ", MONEY = " + gp.player.money + " where ID = 1;";
            s.executeUpdate(sql);

            s.close();
            c.close();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + " " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        mediator.currentMap.draw(g2);
        if(mediator.getGameUIOn())
        {
            mediator.draw(g2);
        }
    }

    @Override
    public void update() {
        if(mediator.keyH.qPressed == true) {
            mediator.keyH.qPressed = false;
            gp.state = State.MAP03PLAYING;
            mediator.currentMap = mediator.maps[2];
            mediator.cChecker.currentMap = mediator.currentMap;
            GameConstants.MAX_SCREEN_COL = mediator.currentMap.cols;
            GameConstants.MAX_SCREEN_ROW = mediator.currentMap.rows;
            mediator.player.currentMap = mediator.currentMap;
            //gp.currentMap.setToSpawnPoint();
            mediator.player.setDefaultValues();
            //GameConstants.SCREEN_WIDTH = GameConstants.TILE_SIZE * GameConstants.MAX_SCREEN_COL;
            //GameConstants.SCREEN_HEIGHT = GameConstants.TILE_SIZE * GameConstants.MAX_SCREEN_ROW;
            //gp.setSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        } else {
            mediator.currentMap.update();
            mediator.player.update();
        }
        if(mediator.keyH.sPressed == true)
        {
            mediator.keyH.sPressed = false;
            System.out.println("Salvez din mapa 2 in baza de date");
            SalveazaJoc();
        }
    }
}
