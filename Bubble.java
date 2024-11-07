
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class Bubble extends JPanel {
    public JTextArea label;
    
    private Color backColor = new Color(255, 255, 225);
    
    public Bubble(String labelText, int fontSize, int height, Point position) {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 0));
        
        add(new JLabel(Resources.getAsImageIcon("resources/bubble-page-start.png")), BorderLayout.PAGE_START);
        add(new JLabel(Resources.getAsImageIcon("resources/bubble-page-end.png")), BorderLayout.PAGE_END);
        
        JPanel leftBorder = new JPanel();
        leftBorder.setBackground(backColor);
        leftBorder.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
        add(leftBorder, BorderLayout.LINE_START);
        
        JPanel rightBorder = new JPanel();
        rightBorder.setBackground(backColor);
        rightBorder.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
        add(rightBorder, BorderLayout.LINE_END);
        
        label = new JTextArea(labelText);
        label.setOpaque(true);
        label.setLineWrap(true);
        label.setWrapStyleWord(true);
        label.setEditable(false);
        label.setBackground(backColor);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        label.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
        add(label, BorderLayout.CENTER);

        setSize(315, height);
        
        // If position is null, spawn in the center of the screen
        if (position == null) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setLocation(
                (screenSize.width / 2) - (getSize().width / 2),
                (screenSize.height / 2) - (getSize().height / 2)
            );
        } else {
            setLocation(position);
        }
        
        for (Component component : new Component[] {this, label}) {
            component.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }
            
            // More empty overrides
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
        });
        }
    }
}