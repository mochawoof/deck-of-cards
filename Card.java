import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

class Card extends JComponent {
    private BufferedImage image;
    private BufferedImage flippedImage;
    private Point position;
    private Card thisCard;
    
    private boolean isMousePressed = false;
    private boolean isFlipped = false;
    
    private JPopupMenu rightClickMenu;
    
    private ArrayList<Card> cards = new ArrayList<Card>();
    
    public Card(BufferedImage image, BufferedImage flippedImage, Point initialPosition) {
        setBounds(0, 0, 200, 200);
        this.image = image;
        this.flippedImage = flippedImage;
        position = initialPosition;
        thisCard = this;
        cards.add(this);
        
        rightClickMenu = new JPopupMenu();
        
        JMenuItem flipItem = new JMenuItem("Flip");
        flipItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                flip();
            }
        });
        rightClickMenu.add(flipItem);
        
        JMenuItem moveAllIntoDeckItem = new JMenuItem("Move All Into Deck");
        rightClickMenu.add(moveAllIntoDeckItem);
        
        JMenuItem moveAllIntoFannedDeckItem = new JMenuItem("Move All Into Fanned Deck");
        rightClickMenu.add(moveAllIntoFannedDeckItem);
        
        JMenuItem shuffleAllItem = new JMenuItem("Shuffle All");
        rightClickMenu.add(shuffleAllItem);
        
        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point mouseOffset = e.getPoint();
                
                // On left click
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isMousePressed = true;
                    if (e.getClickCount() != 2) {
                        ((JLayeredPane) getParent()).moveToFront(thisCard);
                        
                        new SwingWorker() {
                            public Integer doInBackground() {
                                while (isMousePressed) {
                                    Point mousePosition = MouseInfo.getPointerInfo().getLocation();
                                    position = new Point(mousePosition.x - mouseOffset.x, mousePosition.y - mouseOffset.y);
                                    repaint();
                                }                      
                                
                                return 1;
                            }
                        }.execute();
                    } else {
                        flip();
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    // On right click
                    rightClickMenu.show(thisCard, mouseOffset.x, mouseOffset.y);
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isMousePressed = false;
                }
            }
            
            // Define empty overrides to prevent the compiler from bitching
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {}
        });
    }
    
    private void flip() {
        isFlipped = !isFlipped;
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        setBounds(position.x, position.y, image.getWidth(), image.getHeight());
        super.paintComponent(g);
        
        if (!isFlipped) {
            g.drawImage(image, 0, 0, null);
        } else {
            g.drawImage(flippedImage, 0, 0, null);
        }
    }
}