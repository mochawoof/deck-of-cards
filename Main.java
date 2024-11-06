import javax.swing.*;

import javax.imageio.ImageIO;
import java.io.File;

class Main {
    public static Spritesheet sheet;
    
    private static final int CARD_WIDTH = 200;
    
    private static final int DECK_CUT_X = 13;
    private static final int DECK_CUT_Y = 5;
        
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