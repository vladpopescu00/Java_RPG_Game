package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ObjTimePotion extends SuperObject{
    public ObjTimePotion() {
        name = "time potion";
        try {
            BufferedImage tempImage = ImageIO.read(getClass().getResourceAsStream("/potions.png"));
            image = tempImage.getSubimage(32, 0, 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
