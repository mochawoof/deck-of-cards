import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.File;

class Main {
    public static final int CARD_WIDTH = 100;
    public static final int DECK_CUTX = 13; public static final int DECK_CUTY = 5;
    public static final int CARD_COUNT = 52;
    
    private static Spritesheet sheet;
    
    private static Card[] cards;
    
    private static void fatalError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
    
    public static BufferedImage getBackImage() {
        return sheet.get(cardNumberToXY(CARD_COUNT + 1));
    }
    
    private static Point cardNumberToXY(int n) {
        int x = 0;
        int y = 0;
        for (int i=1; i < n; i++) {
            if (x < DECK_CUTX && y < DECK_CUTY) {
                if (x < DECK_CUTX - 1) {
                    x++;
                } else {
                    x = 0;
                    y++;
                }
            } else {
                break;
            }
        }
        return new Point(x, y);
    }
    
    private static void reload_cards() {
        
    }
    
    public static void main(String[] args) {
        try {
            sheet = new Spritesheet(ImageIO.read(new File("deck.png")), DECK_CUTX, DECK_CUTY);
        } catch (Exception e) {
            e.printStackTrace();
            fatalError("Couldn't open card deck image!");
        }
        
        reload_cards();
    }
}