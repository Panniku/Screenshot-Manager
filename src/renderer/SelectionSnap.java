package renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import window.Window;

//import SnippingTool.CenteredImagePanel;

public class SelectionSnap extends JPanel {
	
	private Rectangle selectionBounds;
    private int selectionStartX;
    private int selectionStartY;
    private Cursor customCursor;
    Robot robot;
    
    BufferedImage tempimg, saveimg;
    
    JFrame parent;
    
    public SelectionSnap() {
        try {
             robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setLayout(null);
        customCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        setCursor(customCursor);
        
        Window.keyboardHook.addKeyListener(new GlobalKeyAdapter() {
        	@Override
        	public void keyPressed(GlobalKeyEvent event) {
        		if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) {
        			forceDown();
        		}
        	}
        });
        
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startSelection(e.getX(), e.getY());
            }

            public void mouseReleased(MouseEvent e) {
                int x = (int) selectionBounds.getX();
                int y = (int) selectionBounds.getY();
                int w = (int) selectionBounds.getWidth();
                int h = (int) selectionBounds.getHeight();

                if(w == 0 || h == 0) {
                	//Window.print(String.valueOf(x + " " + y + " "+ w + " " + h));
                	stopSelectionAbrupt();
                	Window.getLogger().logW("Selection is invalid or too small\n");
                }
                
                if(w > 16 && h > 16) {
                	Window.getLogger().logS("Selected Bounds: ");
                    Window.getLogger().logA(w + "x" + h + "\n");
                	//Window.print(String.valueOf(x + " " + y + " "+ w + " " + h));
                	try {
                		BufferedImage img = tempimg.getSubimage(x, y, w, h);
                		saveimg = img;
                		stopSelection();
                	} catch (Exception e1) {
                		e1.printStackTrace(); // do nothing.
                	}
                    
                } else {
                	startSelection(e.getX(), e.getY());
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

    public void stopSelection() {
        selectionBounds = null;
	    setCursor(null);
	    
	    if(saveimg != null) {
	    	parent.dispose();
	        Window.saveScreenShot(saveimg);
	        if(Window.shouldHide) Window.window.setState(JFrame.NORMAL);
	    }
        repaint();
    }
    
    public void stopSelectionAbrupt() {
    	parent.dispose();
    	Window.window.setState(JFrame.NORMAL);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.drawImage(tempimg, 0, 0, getWidth(), getHeight(), null);
        
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

    public void setParentWindow(JFrame parent) {
    	this.parent = parent;
    }
    
    public void snap() {
    	BufferedImage img = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        tempimg = img;
    }
    
    public void forceDown() {
    	stopSelectionAbrupt();
		Window.getLogger().logS("Stopped selection\n");
    }
}
