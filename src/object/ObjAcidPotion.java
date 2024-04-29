package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ObjAcidPotion extends SuperObject{
    public ObjAcidPotion() {
        name = "acid potion";
        try {
            BufferedImage tempImage = ImageIO.read(getClass().getResourceAsStream("/potions.png"));
            image = tempImage.getSubimage(64, 0, 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
