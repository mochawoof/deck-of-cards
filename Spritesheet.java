import java.awt.Point;
import java.awt.image.BufferedImage;

class Spritesheet {
    private BufferedImage image;
    private int cutx; private int cuty;
    
    public Spritesheet(BufferedImage image, int cutx, int cuty) {
        this.image = image;
        this.cutx = cutx; this.cuty = cuty;
    }
    
    public BufferedImage get(int x, int y) {
        int tileWidth = image.getWidth() / cutx;
        int tileHeight = image.getHeight() / cuty;
        return image.getSubimage(tileWidth * x, tileHeight * y, tileWidth, tileHeight);
    }
    
    public BufferedImage get(Point point) {
        return get(point.x, point.y);
    }
}