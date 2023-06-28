package renderer.themed;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

import themes.Palette;
import window.Window;

public class ThemedJButton extends BasicButtonUI {
    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton button = (AbstractButton) c;
        ButtonModel model = button.getModel();
        //button.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
        //button.setPreferredSize(new Dimension(20, 20));
        
        // Customize the background color
        if (model.isRollover()) {
            g.setColor(Color.decode(Palette.gray600));
        } else {
            g.setColor(Color.decode(Palette.gray800));
        }

        g.fillRect(0, 0, button.getWidth(), button.getHeight());
        

        super.paint(g, c);
    }
}