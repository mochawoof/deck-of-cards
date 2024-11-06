import java.awt.Point;
import java.util.ArrayList;

class Card {
    public static ArrayList<Card> cards = new ArrayList<Card>();
    
    public Point sprite;
    public Point location;
    
    public Card(int spritex, int spritey, int locationx, int locationy) {
        sprite = new Point(spritex, spritey);
        location = new Point(locationx, locationy);
        cards.add(this);
    }
    
    public void dispose() {
        cards.remove(this);
    }
}