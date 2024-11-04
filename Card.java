import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

class Card extends JWindow {
    private BufferedImage image;
    private boolean isMouseDown = false;
    private Card thisCard;
    private int height;
    private boolean isFlipped = false;
    
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
        this.image = image;
        
        adjustSize();
        
        setVisible(true);
        
        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isMouseDown = true;
                    Point offset = new Point(e.getXOnScreen() - thisCard.getLocation().x, e.getYOnScreen() - thisCard.getLocation().y);
                    
                    // Bring to front
                    thisCard.setAlwaysOnTop(true);
                    thisCard.setAlwaysOnTop(false);
                    
                    new SwingWorker() {
                        @Override
                        protected Integer doInBackground() {
                            while (isMouseDown) {
                                Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                                thisCard.setLocation(new Point(mouseLocation.x - offset.x, mouseLocation.y - offset.y));
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
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    flip();
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
    
    @Override
    public void paint(Graphics g) {
        adjustSize();
        
        BufferedImage applicableImage = image;
        
        if (isFlipped) {
            applicableImage = Main.getBackImage();
        }
        
        g.drawImage(applicableImage.getScaledInstance(Main.CARD_WIDTH, height, Image.SCALE_SMOOTH), 0, 0, null);
    }
}