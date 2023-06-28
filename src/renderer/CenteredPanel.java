package renderer;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class CenteredPanel extends JPanel {
    private Image image;

//    public CenteredPanel(Image image) {
//        this.image = image;
//    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(image != null) {
        	int x = (getWidth() - image.getWidth(this)) / 2;
            int y = (getHeight() - image.getHeight(this)) / 2;

            g.drawImage(image, x, y, this);
        }
    }
    
    public void setImage(Image image) {
    	this.image = image;
    	this.repaint();
    	this.revalidate();
    }
    
    public Image getImage() {
    	return image;
    }
}