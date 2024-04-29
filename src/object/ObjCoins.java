package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ObjCoins extends SuperObject {
    public ObjCoins() {
        name = "coins";
        try {
            BufferedImage tempImage = ImageIO.read(getClass().getResourceAsStream("/riches.png"));
            image = tempImage.getSubimage(0, 0, 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
