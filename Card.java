import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

class Card extends JWindow {
    private BufferedImage image;
    
    // Used for double buffering
    private JPanel imagePanel;
    
    private boolean isMouseDown = false;
    private Card thisCard;
    private int height;
    private boolean isFlipped = false;
    
    
    private int clickCount = 0;
    private long lastClickTime = 0;
    
    private void adjustSize() {
        double aspect = (double) image.getHeight() / image.getWidth();
        height = (int)(Main.CARD_WIDTH * aspect);
        setSize(Main.CARD_WIDTH, height);
    }
    
    private void flip() {
        isFlipped = !isFlipped;
        repaint();
    }
    
    public Card(BufferedImage image) {
        thisCard = this;
        
        setLayout(new BorderLayout());
        // imagePanel
        imagePanel = new JPanel() {          
            @Override
            public void paintComponent(Graphics g) {
                adjustSize();
                
                BufferedImage applicableImage = image;
                
                if (isFlipped) {
                    applicableImage = Main.getBackImage();
                }
                
                g.drawImage(applicableImage.getScaledInstance(Main.CARD_WIDTH, height, Image.SCALE_SMOOTH), 0, 0, null);
            }
        };
        add(imagePanel, BorderLayout.CENTER);
        
        this.image = image;
        
        adjustSize();
        
        setVisible(true);
        
        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isMouseDown = true;
                    // Mouse offset in card
                    Point offset = new Point(e.getXOnScreen() - thisCard.getLocation().x, e.getYOnScreen() - thisCard.getLocation().y);
                    
                    // Bring to front
                    thisCard.setAlwaysOnTop(true);
                    thisCard.setAlwaysOnTop(false);
                    
                    new SwingWorker() {
                        @Override
                        protected Integer doInBackground() {
                            Point mouseLocation = new Point(e.getXOnScreen(), e.getYOnScreen());
                            while (isMouseDown) {
                                mouseLocation = MouseInfo.getPointerInfo().getLocation();
                                // Card location relative to mouse offset
                                Point relativeLocation = new Point(mouseLocation.x - offset.x, mouseLocation.y - offset.y);
                                
                                thisCard.setLocation(relativeLocation);
                            }
                            
                            // After mouse released
                            // If card wasn't dragged, flip it
                            Point delta = new Point(e.getXOnScreen() - mouseLocation.x, e.getYOnScreen() - mouseLocation.y);
                            if (delta.x == 0 && delta.y == 0) {
                                flip();
                            }
                            
                            return 0;
                        }
                    }.execute();
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isMouseDown = false;
                    
                    // Detect a double click
                    if (lastClickTime < System.currentTimeMillis() - 500) {
                        clickCount = 0;
                    }
                    clickCount++;
                    lastClickTime = System.currentTimeMillis();
                    
                    // If double clicked
                    if (clickCount >= 2) {
                        // Not implemented yet
                        clickCount = 0;
                    }
                }
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
        });
    }
}