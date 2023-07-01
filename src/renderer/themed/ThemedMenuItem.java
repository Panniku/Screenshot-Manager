package renderer.themed;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuItemUI;

public class ThemedMenuItem extends BasicMenuItemUI {
    private static Color hoverBackground; //= new Color(224, 224, 224);  // Custom hover background color
    private static Color hoverForeground; // = Color.BLACK;  // Custom hover foreground color

    public ThemedMenuItem(Color hoverBg, Color hoverFg) {
    	hoverBackground = hoverBg;
    	hoverForeground = hoverFg;
    }
    
    public static ComponentUI createUI(JComponent c) {
        return new ThemedMenuItem(hoverBackground, hoverBackground);
    }

    @Override
    protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
        ButtonModel model = menuItem.getModel();
        Color oldColor = g.getColor();

        if (model.isArmed() || model.isSelected()) {
            g.setColor(hoverBackground);
            g.fillRect(0, 0, menuItem.getWidth(), menuItem.getHeight());
        } else if (model.isRollover()) {
            g.setColor(hoverBackground);
            g.fillRect(0, 0, menuItem.getWidth(), menuItem.getHeight());
        }

        g.setColor(oldColor);
    }

    @Override
    protected void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text) {
        ButtonModel model = menuItem.getModel();
        g.setColor(model.isArmed() || model.isSelected() ? menuItem.getForeground() : hoverForeground);
        super.paintText(g, menuItem, textRect, text);
    }
}
