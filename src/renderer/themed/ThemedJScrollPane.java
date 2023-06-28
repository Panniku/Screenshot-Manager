package renderer.themed;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.swing.FontIcon;

import themes.Palette;

public class ThemedJScrollPane extends JScrollPane {

	public ThemedJScrollPane(Component view) {
		super(view);
		setOpaque(true);
		
		// Create a custom ScrollBarUI for vertical scrollbar
        BasicScrollBarUI verticalScrollBarUI = new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                // Customize scrollbar colors
                this.thumbColor = Color.decode(Palette.gray600);
                this.thumbDarkShadowColor = Color.decode(Palette.gray600);
                this.thumbHighlightColor = Color.decode(Palette.gray400);
                this.thumbLightShadowColor = Color.decode(Palette.gray500);
                this.trackColor = Color.decode(Palette.gray800);
                this.setThumbRollover(true);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
            	// Create a custom decrease button
                JButton button = new JButton(new FontIcon().of(BoxiconsRegular.UP_ARROW_ALT, 15, Color.decode(Palette.gray500)));
                // Set your custom button properties
                button.setOpaque(false);
                button.setFocusPainted(false);
                button.setPreferredSize(new Dimension(17, 17));
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setUI(new BasicButtonUI() {
                	@Override
                    public void paint(Graphics g, JComponent c) {
                        AbstractButton button = (AbstractButton) c;
                        ButtonModel model = button.getModel();
                        if (model.isRollover()) {
                            g.setColor(Color.decode(Palette.gray600));
                        } else {
                            g.setColor(Color.decode(Palette.gray800));
                        }

                        g.fillRect(0, 0, button.getWidth(), button.getHeight());

                        super.paint(g, c);
                    }
                });
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
            	// Create a custom decrease button
                JButton button = new JButton(new FontIcon().of(BoxiconsRegular.DOWN_ARROW_ALT, 15, Color.decode(Palette.gray500)));
                // Set your custom button properties
                button.setOpaque(false);
                button.setFocusPainted(false);
                button.setPreferredSize(new Dimension(17, 17));
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setUI(new BasicButtonUI() {
                	@Override
                    public void paint(Graphics g, JComponent c) {
                        AbstractButton button = (AbstractButton) c;
                        ButtonModel model = button.getModel();
                        if (model.isRollover()) {
                            g.setColor(Color.decode(Palette.gray600));
                        } else {
                            g.setColor(Color.decode(Palette.gray800));
                        }

                        g.fillRect(0, 0, button.getWidth(), button.getHeight());

                        super.paint(g, c);
                    }
                });
                return button;
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                // Customize the painting of the thumb
                g.setColor(Color.decode(Palette.gray600));
                g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
            }
        };

        // Set the custom ScrollBarUI to the vertical scrollbar of JScrollPane
        this.getVerticalScrollBar().setUI(verticalScrollBarUI);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.getVerticalScrollBar().setPreferredSize(new Dimension(15, getHeight()));
        
        
     // Create a custom ScrollBarUI for horizontal scrollbar
        BasicScrollBarUI horizontalScrollBarUI = new BasicScrollBarUI() {
        	@Override
            protected void configureScrollBarColors() {
        		// Customize scrollbar colors
                this.thumbColor = Color.decode(Palette.gray600);
                this.thumbDarkShadowColor = Color.decode(Palette.gray600);
                this.thumbHighlightColor = Color.decode(Palette.gray400);
                this.thumbLightShadowColor = Color.decode(Palette.gray500);
                this.trackColor = Color.decode(Palette.gray800);
                this.setThumbRollover(true);
            }

        	@Override
            protected JButton createDecreaseButton(int orientation) {
            	// Create a custom decrease button
                JButton button = new JButton(new FontIcon().of(BoxiconsRegular.LEFT_ARROW_ALT, 15, Color.decode(Palette.gray500)));
                // Set your custom button properties
                button.setOpaque(false);
                button.setFocusPainted(false);
                button.setPreferredSize(new Dimension(17, 17));
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setUI(new BasicButtonUI() {
                	@Override
                    public void paint(Graphics g, JComponent c) {
                        AbstractButton button = (AbstractButton) c;
                        ButtonModel model = button.getModel();
                        if (model.isRollover()) {
                            g.setColor(Color.decode(Palette.gray600));
                        } else {
                            g.setColor(Color.decode(Palette.gray800));
                        }

                        g.fillRect(0, 0, button.getWidth(), button.getHeight());

                        super.paint(g, c);
                    }
                });
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
            	// Create a custom decrease button
                JButton button = new JButton(new FontIcon().of(BoxiconsRegular.RIGHT_ARROW_ALT, 15, Color.decode(Palette.gray500)));
                // Set your custom button properties
                button.setOpaque(false);
                button.setFocusPainted(false);
                button.setPreferredSize(new Dimension(17, 17));
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setUI(new BasicButtonUI() {
                	@Override
                    public void paint(Graphics g, JComponent c) {
                        AbstractButton button = (AbstractButton) c;
                        ButtonModel model = button.getModel();
                        if (model.isRollover()) {
                            g.setColor(Color.decode(Palette.gray600));
                        } else {
                            g.setColor(Color.decode(Palette.gray800));
                        }

                        g.fillRect(0, 0, button.getWidth(), button.getHeight());

                        super.paint(g, c);
                    }
                });
                return button;
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                // Customize the painting of the thumb
                g.setColor(Color.decode(Palette.gray600));
                g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
            }
        };

        // Set the custom ScrollBarUI to the horizontal scrollbar of JScrollPane
        this.getHorizontalScrollBar().setUI(horizontalScrollBarUI);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.getHorizontalScrollBar().setPreferredSize(new Dimension(getWidth(), 15));
	}

}
