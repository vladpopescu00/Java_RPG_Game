package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ObjBlueRock extends SuperObject{
    public ObjBlueRock() {
        name = "blue rock";
        try {
            BufferedImage tempImage = ImageIO.read(getClass().getResourceAsStream("/riches.png"));
            image = tempImage.getSubimage(32, 0, 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
