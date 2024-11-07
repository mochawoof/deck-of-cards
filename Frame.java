import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.File;

class Frame extends JFrame {
    public Spritesheet sheet;
    
    private final int CARD_SCALE = 150;
    
    private final int CUTX = 13;
    private final int CUTY = 5;
    
    private final int CARDS_PER_DECK = 52;
    
    private void resizeWindow() {
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
    }
    // Card numbers are 1 indexed
    private Point getCardCoords(int cardNumber) {
        int x = 0;
        int y = 0;
        for (int i=1; i < cardNumber; i++) {
            if (x < CUTX - 1) {
                x++;
            } else {
                x = 0;
                y++;
            }
        }
        return new Point(x, y);
    }
    
    public Frame() {
        setUndecorated(true);
        setBackground(new Color(50, 100, 50, 0));
        setTitle("Cards");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Resources.getAsImage("resources/icon.png"));
        
        setLayout(new BorderLayout());
        JLayeredPane container = new JLayeredPane();
        add(container, BorderLayout.CENTER);
        
        // Setup spritesheet
        
        BufferedImage image = null;
        
        try {
            image = ImageIO.read(new File("deck.png"));
        } catch (Exception e) {
            try {
                Image rawImage = Resources.getAsImage("deck.png");
                image = new BufferedImage(rawImage.getWidth(null), rawImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                
                image.getGraphics().drawImage(rawImage, 0, 0, null);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to open deck spritesheet!", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
        
        sheet = new Spritesheet(image, CUTX, CUTY, CARD_SCALE);
        
        // Add cards
        for (int i = 1; i <= CARDS_PER_DECK; i++) {
            Card card = new Card(
                    container,
                    sheet.get(getCardCoords(i)),
                    sheet.get(getCardCoords(CARDS_PER_DECK + 1)),
                    new Point(100, 100)
            );
        }
               
        setVisible(true);
        
        new Bubble(
                container,
                "Welcome to Cards! To move a card, just hold down on it with your mouse and drag it. You can double-click any card to flip it over. Right click on any card for even more options. \n\nClick to dismiss.",
                10,
                120,
                null
        );
    }
    
    @Override
    public void paint(Graphics g) {
        resizeWindow();
        super.paint(g);
    }
}