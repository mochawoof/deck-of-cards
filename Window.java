import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

class Window extends JFrame {
    
    private void resizeWindow() {
        // Resize to fit screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setLocation(new Point(0, 0));
    }
    
    public Window() {
        setTitle("Cards");
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        resizeWindow();
        setBackground(new Color(50, 100, 50, 0));
        setVisible(true);
        
        new Card(this, cardNumberToSpriteCoords(49), new Point(100, 100));
        for (int i=1; i <= Main.DECK_COUNT; i++) {
            new Card(this, cardNumberToSpriteCoords(i), new Point(100 + (i * 2), 100 + (i * 2)));
        }
    }
    
    // Card numbers are 1 indexed
    private Point cardNumberToSpriteCoords(int n) {
        int x = 0;
        int y = 0;
        for (int i=1; i < n; i++) {
            if (x < Main.DECK_CUT_X - 1) {
                x++;
            } else {
                x = 0;
                y++;
            }
        }
        return new Point(x, y);
    }
        
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        resizeWindow();
        
        for (Card card : Card.cards) {
            BufferedImage spriteImage;
            
            if (!card.flipped) {
                spriteImage = Main.sheet.get(card.sprite.x, card.sprite.y);
            } else {
                spriteImage = Main.sheet.get(cardNumberToSpriteCoords(Main.DECK_COUNT + 1));
            }
            
            g.drawImage(spriteImage, card.location.x, card.location.y, null);
        }
    }
}