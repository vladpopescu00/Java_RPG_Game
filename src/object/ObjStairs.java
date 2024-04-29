package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ObjStairs extends SuperObject{
    public ObjStairs() {
        name = "stairs";
        try {
            BufferedImage tempImage = ImageIO.read(getClass().getResourceAsStream("/tilemap.png"));
            image = tempImage.getSubimage(1*16, 2*16, 16, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
