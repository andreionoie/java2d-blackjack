import java.awt.image.BufferedImage;

public class Spritesheet {
    private BufferedImage sheet;
    private int w;
    private int h;

    public Spritesheet(String path, int w, int h) {
        sheet = ImgLoader.loadImage(path);
        this.w = w;
        this.h = h;
    }

    public BufferedImage getTileAt(int x, int y) {
        return sheet.getSubimage(x*w, y*h, w, h);
    }
}
