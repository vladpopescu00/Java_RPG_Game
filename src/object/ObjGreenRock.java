package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ObjGreenRock extends SuperObject{
    public ObjGreenRock() {
        name = "green rock";
        try {
            BufferedImage tempImage = ImageIO.read(getClass().getResourceAsStream("/riches.png"));
            image = tempImage.getSubimage(64, 0, 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
