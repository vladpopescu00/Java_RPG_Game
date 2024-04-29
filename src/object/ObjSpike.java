package object;

import main.GameConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ObjSpike extends SuperObject{

    BufferedImage image2;
    int changeState = 240;
    BufferedImage currentImage;
    public ObjSpike() {
        name = "spike";
        try {
            BufferedImage tempImage = ImageIO.read(getClass().getResourceAsStream("/spikes_spritesheet.png"));
            image = tempImage.getSubimage(7*16, 0, 16, 16);
            image2 = tempImage.getSubimage(0*16, 0*16, 16, 16);
            currentImage = image;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(currentImage, x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE, null);
        if (changeState == 0) {
            if (currentImage == image) {
                name = "spike_off";
                currentImage = image2;
            }
            else if (currentImage == image2) {
                name = "spike";
                currentImage = image;
            }
            changeState = 240;
        } else {
            changeState--;
        }
    }
}
