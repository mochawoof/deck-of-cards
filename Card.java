import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

class Card extends JComponent {
    private BufferedImage image;
    private BufferedImage flippedImage;
    private Card thisCard;
    private JLayeredPane container;
    
    private boolean isMousePressed = false;
    private boolean isFlipped = false;
    
    private JPopupMenu rightClickMenu;
    
    private static ArrayList<Card> cards = new ArrayList<Card>();
    
    public Card(JLayeredPane container, BufferedImage image, BufferedImage flippedImage, Point initialPosition) {
        setBounds(0, 0, 200, 200);
        this.image = image;
        this.flippedImage = flippedImage;
        setBounds(initialPosition.x, initialPosition.y, image.getWidth(), image.getHeight());
        thisCard = this;
        this.container = container;
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
        moveAllIntoDeckItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < cards.size(); i++) {
                    cards.get(i).setBounds(100, 100, image.getWidth(), image.getHeight());
                    container.moveToFront(cards.get(i));
                }
                refresh();
            }
        });
        rightClickMenu.add(moveAllIntoDeckItem);
        
        JMenuItem moveAllIntoFannedDeckItem = new JMenuItem("Move All Into Fanned Deck");
        moveAllIntoFannedDeckItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < cards.size(); i++) {
                    cards.get(i).setBounds(100 + (i * 2), 100 + (i * 2), image.getWidth(), image.getHeight());
                    container.moveToFront(cards.get(i));
                }
                refresh();
            }
        });
        rightClickMenu.add(moveAllIntoFannedDeckItem);
        
        JMenuItem shuffleAllItem = new JMenuItem("Shuffle All");
        shuffleAllItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < cards.size(); i++) {
                    int randIndex = (int) Math.floor(Math.random() * (cards.size() - 1));
                    
                    Rectangle firstBounds = cards.get(i).getBounds();
                    int firstLayer = container.getLayer(cards.get(i));
                    int firstLayerPosition = container.getPosition(cards.get(i));
                    
                    Rectangle secondBounds = cards.get(randIndex).getBounds();
                    int secondLayer = container.getLayer(cards.get(randIndex));
                    int secondLayerPosition = container.getPosition(cards.get(randIndex));
                    
                    cards.get(i).setBounds(secondBounds);
                    container.setLayer(cards.get(i), secondLayer);
                    container.setPosition(cards.get(i), secondLayerPosition);
                    
                    cards.get(randIndex).setBounds(firstBounds);
                    container.setLayer(cards.get(randIndex), firstLayer);
                    container.setPosition(cards.get(randIndex), firstLayerPosition);
                }
                refresh();
            }
        });
        rightClickMenu.add(shuffleAllItem);
        
        JMenuItem flipAllItem = new JMenuItem("Flip All");
        flipAllItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean isAllFlipped = true;
                for (int i = 0; i < cards.size(); i++) {
                    if (isAllFlipped && !cards.get(i).isFlipped) {
                        isAllFlipped = false;
                    }
                }
                for (int i = 0; i < cards.size(); i++) {
                    cards.get(i).isFlipped = !isAllFlipped;
                }
                refresh();
            }
        });
        rightClickMenu.add(flipAllItem);
        
        container.add(this);
        container.moveToFront(this);
        
        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point mouseOffset = e.getPoint();
                
                // On left click
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isMousePressed = true;
                    if (e.getClickCount() != 2) {
                        container.moveToFront(thisCard);
                        
                        new SwingWorker() {
                            public Integer doInBackground() {
                                while (isMousePressed) {
                                    Point mousePosition = MouseInfo.getPointerInfo().getLocation();
                                    setBounds(mousePosition.x - mouseOffset.x, mousePosition.y - mouseOffset.y, image.getWidth(), image.getHeight());
                                    refresh();
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
    
    private void refresh() {
        container.repaint();
    }
    
    private void flip() {
        isFlipped = !isFlipped;
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (!isFlipped) {
            g.drawImage(image, 0, 0, null);
        } else {
            g.drawImage(flippedImage, 0, 0, null);
        }
    }
}