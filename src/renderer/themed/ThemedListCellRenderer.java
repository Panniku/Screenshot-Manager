package renderer.themed;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

public class ThemedListCellRenderer extends DefaultListCellRenderer {
	
	private Color BACKGROUND_COLOR;
	private Color FOREGROUND_COLOR;
	private Color HIGH_BACKGROUND_COLOR;
	private Color HIGH_FOREGROUND_COLOR;
	
	public ThemedListCellRenderer(Color background, Color foreground, Color highBackround, Color highForeground) {
		this.BACKGROUND_COLOR = background;
		this.FOREGROUND_COLOR = foreground;
		this.HIGH_BACKGROUND_COLOR = highBackround;
		this.HIGH_FOREGROUND_COLOR = highForeground;
		
		setFocusable(false);
		setBorder(BorderFactory.createEmptyBorder());
	}
	
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setOpaque(true);
        
        if(isSelected) {
        	label.setBackground(HIGH_BACKGROUND_COLOR);  
            label.setForeground(HIGH_FOREGROUND_COLOR);  
        } else {
            label.setBackground(BACKGROUND_COLOR);  
            label.setForeground(FOREGROUND_COLOR);  
        }
        
        return label;
    }
}
