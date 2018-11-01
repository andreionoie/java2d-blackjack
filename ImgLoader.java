import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

// credit: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src

public class ImgLoader {

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ImgLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}