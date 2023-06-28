package renderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.plaf.basic.BasicButtonUI;

import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.swing.FontIcon;

import renderer.themed.ThemedJButton;
import themes.Palette;
import utils.CopyImagetoClipBoard;
import utils.Logger;
import window.Window;

public class LogPanel extends JPanel {

	JPanel imagePanel;
	CenteredPanel image;
	JLabel infoText;
	//
	JPanel optionBox;
	JButton delButton, previewButton, editButton, copyButton;
	//
	String path;
	
	public LogPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder());
		setMaximumSize(new Dimension(370, 105));
		setBackground(Color.decode(Palette.gray800));
		//setMinimumSize(new Dimension(170, 110));
		setPreferredSize(new Dimension(187, 105));
		
		//

		imagePanel = new JPanel();
		imagePanel.setLayout(new BorderLayout());
		imagePanel.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		imagePanel.setBackground(Color.decode(Palette.gray800));
		
		image = new CenteredPanel();
		image.setPreferredSize(new Dimension(160, 90));
		image.setBackground(Color.decode(Palette.gray800));
		
		infoText = new JLabel();
		infoText.setForeground(Color.decode(Palette.gray500));
		//infoText.setBackground(Color.BLACK);
		
		//
		
		optionBox = new JPanel();
		optionBox.setLayout(new BoxLayout(optionBox, BoxLayout.Y_AXIS));
		optionBox.setPreferredSize(new Dimension(25, 25));
		optionBox.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		optionBox.setBackground(Color.decode(Palette.gray500));
		
		delButton = new JButton(new FontIcon().of(BoxiconsRegular.TRASH, 15, Color.RED));
		delButton.setToolTipText("<html>Remove from log</html>");
		delButton.setPreferredSize(new Dimension(25, 25));
		delButton.setMaximumSize(new Dimension(25, 25));
		delButton.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		delButton.setBackground(Color.decode(Palette.gray800));
		delButton.setUI(new ThemedJButton());
		
		previewButton = new JButton(new FontIcon().of(BoxiconsRegular.IMAGE_ALT, 15, Color.decode(Palette.gray500)));
		previewButton.setToolTipText("<html>Preview image<br>Preview mode is needed to preview</html>");
		previewButton.setPreferredSize(new Dimension(25, 25));
		previewButton.setMaximumSize(new Dimension(25, 25));
		previewButton.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		previewButton.setBackground(Color.decode(Palette.gray800));
		previewButton.setUI(new ThemedJButton());
		
		editButton = new JButton(new FontIcon().of(BoxiconsRegular.EDIT, 15, Color.decode(Palette.gray500)));
		editButton.setToolTipText("<html>Edit image</html>");
		editButton.setPreferredSize(new Dimension(20, 20));
		editButton.setMaximumSize(new Dimension(25, 25));
		editButton.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		editButton.setBackground(Color.decode(Palette.gray800));
		editButton.setUI(new ThemedJButton());
		
		copyButton = new JButton(new FontIcon().of(BoxiconsRegular.COPY, 15, Color.decode(Palette.gray500)));
		copyButton.setToolTipText("<html>Copy image to clipboard</html>");
		copyButton.setPreferredSize(new Dimension(20, 20));
		copyButton.setMaximumSize(new Dimension(25, 25));
		copyButton.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		copyButton.setBackground(Color.decode(Palette.gray800));
		copyButton.setUI(new ThemedJButton());
		

		
		// Events

		delButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Window.print("Clicked copy" );
			}
		});
		//
		previewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Window.setPreviewImage(getImagePath());
				Window.prvwPanel.revalidate();
				Window.prvwPanel.repaint();
			}
		});
		//
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Window.print("Clicked copy" );
			}
		});
		//
		copyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					BufferedImage img = ImageIO.read(new File(getImagePath()));
					CopyImagetoClipBoard ci = new CopyImagetoClipBoard();
					ci.copyImage(img);
					Window.getLogger().logS("Copied to clipboard.");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					Window.getLogger().logE("Failed to copy to clipboard.");
				}
				
			}
		});
		
		
		optionBox.add(delButton);
		optionBox.add(previewButton);
		optionBox.add(editButton);
		optionBox.add(copyButton);
		
		imagePanel.add(image, BorderLayout.CENTER);
		imagePanel.add(infoText, BorderLayout.SOUTH);;
		
		add(imagePanel, BorderLayout.CENTER);
		add(optionBox, BorderLayout.EAST);		
	}
	
	public void setImage(ImageIcon icon) {
		//image.setIcon(icon);
		image.setImage(icon.getImage());
	}

	public void setText(String text) {
		infoText.setText(text);
	}
	 
	public void setPath(String path) {
		this.path = path;
	}

	public String getImagePath() {
		return path;		
	}
	 
	 //public String[]
}

