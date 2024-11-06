import java.awt.*;
import java.awt.image.BufferedImage;

class Spritesheet {
    private int cutx; private int cuty;
    private BufferedImage image;
    private int tileScale;
    
    public Spritesheet(BufferedImage image, int cutx, int cuty, int tileScale) {
        this.cutx = cutx;
        this.cuty = cuty;
        this.image = image;
        this.tileScale = tileScale;
    }
    
    public BufferedImage get(int x, int y) {
        int tileWidth = image.getWidth() / cutx;
        int tileHeight = image.getHeight() / cuty;        
        BufferedImage subImage = image.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
        
        double aspect = (double) tileHeight / tileWidth;
        Image scaledSubImage = subImage.getScaledInstance(tileScale, (int) ((double) tileScale * aspect), Image.SCALE_SMOOTH);
        
        BufferedImage finalImage = new BufferedImage(scaledSubImage.getWidth(null), scaledSubImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        finalImage.getGraphics().drawImage(scaledSubImage, 0, 0, null);
        
        return finalImage;
    }
    
    public BufferedImage get(Point coords) {
        return get(coords.x, coords.y);
    }
}