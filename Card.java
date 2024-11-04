import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class Card extends JWindow {
    private Image image;
    private boolean isMouseDown = false;
    private Card thisCard;
    
    public Card(Image image) {
        thisCard = this;
        
        this.image = image;
        setSize(Main.CARD_WIDTH, Main.CARD_HEIGHT);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isMouseDown = true;
                Point mouseOffset = e.getPoint();
                
                new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        while (isMouseDown) {
                            Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                            
                            Point relativeLocation = new Point(mouseLocation.x - mouseOffset.x, mouseLocation.y - mouseOffset.y);
                            
                            thisCard.setLocation(relativeLocation);
                        }
                        return 0;
                    }
                }.execute();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                isMouseDown = false;
            }
        });
        
        setVisible(true);
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}