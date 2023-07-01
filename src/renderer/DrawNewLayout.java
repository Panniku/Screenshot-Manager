package renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import constructors.Layouts;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import window.Window;

public class DrawNewLayout extends JPanel {
	
	private DrawNewLayoutCallback callback;
	
	private Rectangle selectionBounds;
    private int selectionStartX;
    private int selectionStartY;
    private Cursor customCursor;
    Robot robot;
    
    Image img;

    JFrame root;
    //
    JFrame parent;
    JDialog dialog;

    int finalize;
    Layouts newLayout;
    String name;
    
    public DrawNewLayout(JFrame parent, JDialog dialog, String name, DrawNewLayoutCallback callback) {
        try {
             robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.parent = parent;
        this.dialog = dialog;
        this.name = name;
        this.callback = callback;
        //
        //parent.setVisible(false);
        parent.setState(JFrame.ICONIFIED);
        dialog.setVisible(false);
        
        
        root = new JFrame();
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setUndecorated(true);
		root.setExtendedState(JFrame.MAXIMIZED_BOTH);
		root.setVisible(true);
		root.setLocationRelativeTo(null);
		//root.setAlwaysOnTop(true);
		root.add(this);

		img = snap();
		revalidate();
		repaint();
		
        setLayout(null);
        customCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        setCursor(customCursor);
        
        JOptionPane.showMessageDialog(null, "Click and drag to make selection.\nOnce selected, Click \"OK\" to finalize layout selection.");
        
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startSelection(e.getX(), e.getY());
            }

            public void mouseReleased(MouseEvent e) {
            	finalize = JOptionPane.showConfirmDialog(null, "Confirm selection?", "DrawNewLayout", JOptionPane.YES_NO_CANCEL_OPTION);
            	switch(finalize) {
            		case JOptionPane.YES_OPTION:
            			String x = String.valueOf((int) selectionBounds.getX());
            			String y = String.valueOf((int) selectionBounds.getY());
            			String w = String.valueOf((int) selectionBounds.getMaxX());
            			String h = String.valueOf((int) selectionBounds.getMaxY());
            			String[] obj = {x, y, w, h};
            			//
            			Window.print(obj[0] + " " + obj[1] + " " + obj[2] + " " + obj[3]);
            			newLayout = new Layouts(name, obj);
            			setNewLayout(newLayout);
            			callback.onLayoutAdded(newLayout);
            			stop();
            			break;
            			
            		case JOptionPane.NO_OPTION:
            			startSelection(e.getX(), e.getY());
            			break;
            			
            		case JOptionPane.CANCEL_OPTION:
            			//Window.print(Window.getFormattedDateTime() + "done sel");
            			stop();
            			break;
            			
            	}
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                updateSelection(e.getX(), e.getY());
            }
        });
        
        
    }

    public void startSelection(int x, int y) {
    	repaint();
    	//Window.window.setVisible(false);
    	
        selectionStartX = x;
        selectionStartY = y;
        selectionBounds = new Rectangle(x, y, 0, 0);
        setCursor(customCursor);
        repaint();
    }

    public void updateSelection(int x, int y) {
        int width = Math.abs(x - selectionStartX);
        int height = Math.abs(y - selectionStartY);
        int startX = Math.min(x, selectionStartX);
        int startY = Math.min(y, selectionStartY);

        selectionBounds.setBounds(startX, startY, width, height);
        repaint();
    }
    
    public void stop() {
    	root.dispose();
    	parent.setState(JFrame.NORMAL);
    	dialog.setVisible(true);
    	
    	//Window.window.setState(JFrame.NORMAL);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        
        // Apply a slightly dark overlay
        g2d.setColor(new Color(0, 0, 0, 64)); // Black with transparency
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        if (selectionBounds != null) {
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(1)); // Set the line thickness to 3 pixels
            g2d.drawRect(selectionBounds.x, selectionBounds.y, selectionBounds.width, selectionBounds.height);
            
            String text = selectionBounds.width + "x" + selectionBounds.height;
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();

            int x = selectionBounds.x + (selectionBounds.width - textWidth) / 2;
            int y = selectionBounds.y + (selectionBounds.height + textHeight) / 2;

            g.drawString(text, x, y);
        }
    }

    @Override
    public void setCursor(Cursor cursor) {
        super.setCursor(customCursor);
    }
    	
    public Image snap() {
    	return robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public void setNewLayout(Layouts newLayout) {
    	this.newLayout = newLayout;
    }
    
    public Layouts getNewLayout() {
    	return newLayout;
    }
    
    public interface DrawNewLayoutCallback {
        void onLayoutAdded(Layouts newLayout);
    }
    
}
