package renderer.themed;

import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuItemUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

import java.awt.*;

public class ThemedPopupMenuUI extends BasicPopupMenuUI {
	
	private Color BACKGROUND_COLOR;
	
	public ThemedPopupMenuUI(Color background) {
		this.BACKGROUND_COLOR = background;
	}
	
    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(BACKGROUND_COLOR);  // Customize the background color of popup menu
        g2.fillRect(0, 0, c.getWidth(), c.getHeight());
    }
}