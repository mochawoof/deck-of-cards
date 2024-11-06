import java.awt.Point;

class Card {
    public static Card[] cards = new Card[0];
    
    public Point sprite;
    public Point location;
    
    public boolean flipped = false;
    
    public Card(Window parent, Point sprite, Point location) {
        this.sprite = sprite;
        this.location = location;
        
        // Add this card to cards
        addMe();
        
        parent.repaint();
    }
    
    private void addMe() {
        Card[] oldCards = cards;
        cards = new Card[oldCards.length + 1];
        for (int i=0; i < oldCards.length; i++) {
            cards[i] = oldCards[i];
        }
        cards[cards.length - 1] = this;
    }
        
    public Card(Window parent, int spritex, int spritey, int locationx, int locationy) {
        this(parent, new Point(spritex, spritey), new Point(locationx, locationy));
    }
    
    public void bringToFront() {
        dispose();
        addMe();
    }
    
    public void dispose() {
        // Make this card null
        Card[] oldCards = cards;
        for (int i=0; i < oldCards.length; i++) {
            if (oldCards[i].hashCode() == this.hashCode()) {
                oldCards[i] = null;
            }
        }
        
        // Shift cards to cover this dead card
        cards = new Card[oldCards.length - 1];
        boolean isShifting = false;
        for (int i=0; i < cards.length; i++) {
            if (oldCards[i] == null) {
                isShifting = true;
            }
            if (isShifting) {
                cards[i] = oldCards[i + 1];
            } else {
                cards[i] = oldCards[i];
            }
        }
    }
}