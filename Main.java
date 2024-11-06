import javax.swing.*;

import javax.imageio.ImageIO;
import java.io.File;

class Main {
    public static Spritesheet sheet;
    
    // Setting CARD_WIDTH to 0 disables card scaling
    public static final double CARD_WIDTH = 0;
    
    public static final int DECK_CUT_X = 13;
    public static final int DECK_CUT_Y = 5;
    public static final int DECK_COUNT = 52;
        
    public static void main(String[] args) {
        try {
            sheet = new Spritesheet(ImageIO.read(new File("deck.png")), DECK_CUT_X, DECK_CUT_Y);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load deck spritesheet!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        new Window();
    }
}