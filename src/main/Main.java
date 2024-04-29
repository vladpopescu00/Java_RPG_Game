package main;

import javax.swing.*;
import java.sql.*;


// CE VREAU SA ADAUG IN JOC:
// MENIU
// INAMICI INTELIGENTI
// REZOLVARE COLIZIUNI COMPLETA (NU CUM ACUMA, 10.06.2023)
//


public class Main {
    public static void CreazaBaza() {
        Connection c = null;
        Statement s = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:The_Lust_for_Gold.db");
            s = c.createStatement();

//            String sql_database = "CREATE TABLE Score " +
//                    "(ID INT PRIMARY KEY NOT NULL," +
//                    "MONEY INT)";
//            s.execute(sql_database);

//            String sql = "INSERT INTO Score (ID, MONEY) " +
//                    "VALUES (1, 0);";
//            s.executeUpdate(sql);

//            String sql_database = "CREATE TABLE GameData " +
//                    "(ID INT PRIMARY KEY NOT NULL," +
//                    "MAP INT," +
//                    "POSITIONX INT, " +
//                    "POSITIONY INT, " +
//                    "HEALTH INT," +
//                    "MONEY INT)";
//            s.execute(sql_database);


//            String sql = "INSERT INTO GameData (ID, MAP, POSITIONX, POSITIONY, HEALTH, MONEY) " +
//                    "VALUES (1, 0, 0, 0, 0, 0);";
//            s.executeUpdate(sql);


            ResultSet rs = s.executeQuery("SELECT * FROM GameData");
            while(rs.next())
            {
                int id = rs.getInt("ID");
                int map = rs.getInt("MONEY");
                int money = rs.getInt("MONEY");
                System.out.println("ID = " + id + " Money = " + money + " Map = " + map);
            }

            s.close();
            c.close();
        } catch (Exception e) {
            System.out.println("ERROR " + e.getClass().getName() + " " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setResizable(false);
        win.setTitle("The Lust of Gold");

        GamePanel gameInstance = GamePanel.getInstance();
        win.add(gameInstance);

        win.pack();

        win.setLocationRelativeTo(null);
        win.setVisible(true);

        gameInstance.startGameThread();
    }
}