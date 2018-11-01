import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

// credit: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src

public class ImgUtilities {

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ImgUtilities.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    // credits: https://codereview.stackexchange.com/a/58177

    public static void addImg(BufferedImage buff1, BufferedImage buff2, float opaque) {
        Graphics2D g2d = buff1.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opaque));
        g2d.drawImage(buff2, 0, 0, null);
        g2d.dispose();
    }

}