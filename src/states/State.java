package states;

import java.awt.*;

public interface State {

    public static final int MENU = 0;
    public static final int MAP01PLAYING = 1;
    public static final int MAP02PLAYING = 2;
    public static final int MAP03PLAYING = 3;
    public static final int INGAME = 4;


    public void draw(Graphics2D g2);
    public void update();
}
