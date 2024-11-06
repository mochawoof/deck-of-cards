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
        resizeWindow();
        setBackground(new Color(50, 100, 50, 0));
        setVisible(true);
        
        new Card(0, 0, 100, 200);
    }
    
        
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        resizeWindow();
        
        Card.cards.forEach((card) -> {
            BufferedImage spriteImage = Main.sheet.get(card.sprite.x, card.sprite.y);
            g.drawImage(spriteImage, card.location.x, card.location.y, null);
        });
    }
}