package renderer.themed;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;

import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.swing.FontIcon;

import constructors.Layouts;
import themes.Palette;

import java.awt.*;

public class ThemedJComboBox extends JComboBox {
    private Color backgroundColor = Color.decode(Palette.gray800);
    private Color textColor = Color.decode(Palette.gray500);

    public ThemedJComboBox() {
        super();
        setOpaque(false);
        setRenderer(new ThemedComboBoxCellRenderer());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Call superclass paintComponent to ensure the text is painted
        super.paintComponent(g2);

        // Fill the background with the custom color
        g2.setColor(backgroundColor);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw the selected item's text
        g2.setColor(textColor);
        Object selectedItem = getSelectedItem();
        if (selectedItem != null) {
            String text = selectedItem.toString();
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            int x = 5; // Set the x-coordinate to a fixed value, e.g., 5
            int y = (getHeight() - textHeight) / 2 + fm.getAscent();
            g2.drawString(text, x, y);
        }

        g2.dispose();
    }

    @Override
    public void updateUI() {
        // Override the updateUI method to prevent the VM from resetting the look and feel
        // This ensures that the custom colors are retained
        setUI(new CustomComboBoxUI());
    }

    static class CustomComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            // Override the arrow button creation to use a custom button with a different color
            JButton button = new JButton(new FontIcon().of(BoxiconsRegular.DOWN_ARROW_ALT,15, Color.decode(Palette.gray500)));
            button.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
            button.setBackground(Color.decode(Palette.gray800));
            button.setUI(new ThemedJButton());
            
            return button;
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            // Override the method to set a different color for the main component's background
            //g.setColor(Color.GREEN);
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }

        @Override
        protected ListCellRenderer createRenderer() {
            // Override the renderer creation to use a custom renderer with different colors
            return new ThemedComboBoxCellRenderer();
        }
    }
    
    static class ThemedComboBoxCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof Layouts) {
                Layouts layouts = (Layouts) value;
                
                //list.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
                setForeground(Color.decode(Palette.gray500));
                if(isSelected) {
                	setForeground(Color.decode(Palette.gray50));
                    setBackground(Color.decode(Palette.gray600));
                } else {
                	setForeground(Color.decode(Palette.gray500));
                    setBackground(Color.decode(Palette.gray800));
                }
                
                renderer.setText(layouts.getName());            
            }

            return renderer;
        }
    }
}
