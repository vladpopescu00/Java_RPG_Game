package states;

import main.GamePanel;
import main.GameUI;
import mediator.Mediator;

import java.awt.*;

public class MenuState implements State{
    GamePanel gp;
    Mediator mediator;

    public MenuState(GamePanel gp, Mediator mediator) {
        this.gp = gp;
        this.mediator = mediator;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setFont(new Font("Times New Roman", Font.ITALIC, 50));
        g2.setColor(Color.GRAY);
        g2.drawString("The Lust for Gold", 200, 200);
        g2.drawString("Press Q to Start", 200, 260);
//        if(mediator.getGameUIOn())
//        {
//            mediator.draw(g2);
//        }
    }

    @Override
    public void update() {
        if(mediator.keyH.qPressed == true)
        {
            mediator.keyH.qPressed = false;
            gp.state = State.INGAME;
            mediator.player.currentMap = mediator.maps[0];
            mediator.player.setDefaultValues();
            mediator.setGameUIOn(true);
            //gp.currentMap.setToSpawnPoint();
        }
        if(mediator.keyH.sPressed == true)
        {
            mediator.keyH.sPressed = false;
            System.out.println("Acceses salvarea din baza de date");
        }
    }
}
