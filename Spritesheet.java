import java.awt.*;
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
        
        BufferedImage subImage = image.getSubimage(tileWidth * x, tileHeight * y, tileWidth, tileHeight);
        
        if (Main.CARD_WIDTH > 0) {
            double aspect = (double) tileHeight / tileWidth;
            int cardHeight = (int) ((double) Main.CARD_WIDTH * aspect);
            
            int scaleMode = Image.SCALE_SMOOTH;
            if (Main.POTATO_MODE) {
                scaleMode = Image.SCALE_FAST;
            }
            
            Image scaledRawImage = subImage.getScaledInstance(Main.CARD_WIDTH, cardHeight, scaleMode);
            subImage = new BufferedImage(Main.CARD_WIDTH, cardHeight, BufferedImage.TYPE_INT_ARGB);
            subImage.getGraphics().drawImage(scaledRawImage, 0, 0, null);
        }
        
        return subImage;
    }
    
    public BufferedImage get(Point point) {
        return get(point.x, point.y);
    }
}