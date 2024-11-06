
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class Bubble extends JPanel {
    public JTextArea label;
    
    public Bubble(String labelText, int fontSize, Point position) {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 0));
        
        add(new JLabel(Resources.getAsImageIcon("resources/bubble-page-start.png")), BorderLayout.PAGE_START);
        add(new JLabel(Resources.getAsImageIcon("resources/bubble-page-end.png")), BorderLayout.PAGE_END);
        add(new JLabel(Resources.getAsImageIcon("resources/bubble-line-start.png")), BorderLayout.LINE_START);
        add(new JLabel(Resources.getAsImageIcon("resources/bubble-line-end.png")), BorderLayout.LINE_END);
        
        label = new JTextArea(labelText);
        label.setOpaque(true);
        label.setLineWrap(true);
        label.setWrapStyleWord(true);
        label.setEditable(false);
        label.setBackground(new Color(255, 255, 225));
        label.setForeground(new Color(0, 0, 0));
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        add(label, BorderLayout.CENTER);
        
        setSize(315, 100);
        
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