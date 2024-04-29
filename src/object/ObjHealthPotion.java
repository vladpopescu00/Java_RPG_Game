package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ObjHealthPotion extends SuperObject{
    public ObjHealthPotion() {
        name = "health potion";
        try {
            BufferedImage tempImage = ImageIO.read(getClass().getResourceAsStream("/potions.png"));
            image = tempImage.getSubimage(0, 0, 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
