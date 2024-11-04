import javax.swing.*;

import java.awt.*;

import java.io.File;

import java.util.Arrays;

class Main {
    public static final int CARD_WIDTH = 207;
    public static final int CARD_HEIGHT = 284;
    
    public static Image[] cardImages = new Image[53];
    public static Card[] cards = new Card[53];
    
    private static void error(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private static void fatalError(String message) {
        error(message);
        System.exit(1);
    }
    
    public static void main(String[] args) {
        // Gather all card images
        File cardFolder = new File("cards/");
        if (cardFolder.exists()) {
            File[] cardFiles = cardFolder.listFiles();
            
            // Sort card files
            // TBD
            
            for (int i=0; i < cards.length; i++) {
                cardImages[i] = new ImageIcon(cardFiles[i].getPath()).getImage().getScaledInstance(Main.CARD_WIDTH, Main.CARD_HEIGHT, Image.SCALE_SMOOTH);
            }
        } else {
            fatalError("No cards were found! Make sure you have all the images in the cards folder.");
        }
        
        // Now the cards can be displayed
        Point initialLocation = new Point(50, 50);
        for (int i=0; i < cardImages.length; i++) {
            cards[i] = new Card(cardImages[i]);
            cards[i].setLocation(initialLocation);
        }
    }
}