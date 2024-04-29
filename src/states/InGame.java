package states;

import main.GameConstants;
import main.GamePanel;
import mediator.Mediator;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


// STAREA PRINCIPALA
// de rulare a jocului, efectiv sunt in joc (nu meniu, setari etc.)
public class InGame implements State{

    GamePanel gp;
    Mediator mediator;
    int currentMapNumber = 1;

    public InGame(GamePanel gp, Mediator mediator) {
        this.gp = gp;
        this.mediator = mediator;
    }

    public void SaveData() {
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

            String sql = "UPDATE GameData SET MAP = " + currentMapNumber + ", POSITIONX = " + gp.player.x
                    + ", POSITIONY = " + gp.player.y + ", HEALTH = " + gp.player.health
                    + ", MONEY = " + gp.player.money + " where ID = 1;";
            s.executeUpdate(sql);

            s.close();
            c.close();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + " " + e.getMessage());
        }
    }

    public void SaveStats() {
        int maxScor = 0;
        int actualScorJoc = gp.player.money;

        Connection c = null;
        Statement s = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:The_Lust_for_Gold.db");
            s = c.createStatement();

            ResultSet rs = s.executeQuery("SELECT * FROM Score");
            while(rs.next())
            {
                int id = rs.getInt("ID");
                maxScor = rs.getInt("MONEY");
                System.out.println("ID = " + id + " Money = " + maxScor);
            }

            if(actualScorJoc > maxScor)
            {
                String sql = "UPDATE Score set MONEY = " + actualScorJoc + " WHERE ID = 1";
                s.executeUpdate(sql);
            }

            s.close();
            c.close();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        mediator.currentMap.draw(g2);
        if(mediator.getGameUIOn()) {
            mediator.draw(g2);
        }
    }

    // PE SPACE salvez scorul (doar mapa 3)
    // PE S salvez datele din joc (din orice harta)
    @Override
    public void update() {
        if(mediator.keyH.qPressed == true) {
            mediator.keyH.qPressed = false;
            if(mediator.currentMap == mediator.maps[0]) {
                mediator.currentMap = mediator.maps[1];
                currentMapNumber = 2;

            } else if(mediator.currentMap == mediator.maps[1]) {
                mediator.currentMap = mediator.maps[2];
                currentMapNumber = 3;

            } else if(mediator.currentMap == mediator.maps[2]) {
                mediator.currentMap = mediator.maps[0];
                currentMapNumber = 1;
                gp.state = State.MENU;
                mediator.setGameUIOn(false);

            }
            GameConstants.MAX_SCREEN_COL = mediator.currentMap.cols;
            GameConstants.MAX_SCREEN_ROW = mediator.currentMap.rows;
            mediator.cChecker.currentMap = mediator.currentMap;
            mediator.player.currentMap = mediator.currentMap;
            mediator.player.setDefaultValues();
        } else {
            mediator.currentMap.update();
            mediator.player.update();
        }

        if(mediator.keyH.sPressed == true) {

            mediator.keyH.sPressed = false;

            if(mediator.currentMap == mediator.maps[0]) {
                System.out.println("Salvez din mapa 1 in baza de date");
                SaveData();
            } else if(mediator.currentMap == mediator.maps[1]) {
                System.out.println("Salvez din mapa 2 in baza de date");
                SaveData();
            } else if(mediator.currentMap == mediator.maps[2]) {
                System.out.println("Salvez din mapa 3 in baza de date");
                SaveData();
            }

        }

        if(mediator.keyH.spacePressed == true) {

            mediator.keyH.spacePressed = false;

            if(mediator.currentMap == mediator.maps[2]) {
                System.out.println("Salvez scorul actual in baza de date din mapa 3 (exclusiv)");
                SaveStats();
            } else {
                System.out.println("NU SUNT IN MAPA 3");
            }
        }

    }
}
