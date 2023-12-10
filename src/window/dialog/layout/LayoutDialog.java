package window.dialog.layout;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.swing.FontIcon;

import constructors.Layouts;
import data.ConfigHandler;
import renderer.CenteredPanel;
import renderer.DrawNewLayout;
import renderer.DrawNewLayout.DrawNewLayoutCallback;
import renderer.themed.ThemedJScrollPane;
import renderer.themed.ThemedListCellRenderer;
import renderer.themed.ThemedMenuItem;
import renderer.themed.ThemedPopupMenuUI;
import themes.Palette;
import window.Window;

public class LayoutDialog extends JDialog{

	JPanel root;
	//
	JSplitPane splitPane;
	JPanel layoutListPanel, layoutSettingPanel;
	//
	JTextField searchbox;
	JButton addLayout;
	JList<Layouts> list;
	JPopupMenu popup;
	JMenuItem edit, delete;
	//
	JLabel layoutName;
	JButton editLayout;
	//JPanel previewContainer;
	CenteredPanel layoutPreview;
	JLabel layoutInfo;
	
	//
	DefaultListModel model;
	ArrayList<Layouts> layoutData;
	//
	int selected;
	
	public LayoutDialog(JFrame parent) {
		super(parent, "Layouts", true);
		setSize(400, 300);
		setResizable(false);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);	      
	      
		root = new JPanel();
		root.setLayout(new BorderLayout());
	      
		//
	      
		layoutListPanel = new JPanel();
		layoutListPanel.setLayout(new BorderLayout());
		layoutListPanel.setPreferredSize(new Dimension(200, 300));
		layoutListPanel.setMinimumSize(new Dimension(150, this.getHeight()));
		layoutListPanel.setBackground(Color.RED);
	      
		layoutSettingPanel = new JPanel();
		layoutSettingPanel.setLayout(new BorderLayout());
		layoutSettingPanel.setPreferredSize(new Dimension(200, 300));
		layoutSettingPanel.setMinimumSize(new Dimension(160, this.getHeight()));
		layoutSettingPanel.setBackground(Color.BLUE);
		  
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, layoutListPanel, layoutSettingPanel);
		splitPane.setContinuousLayout(true);
		splitPane.setDividerLocation(this.getWidth()/2);
		splitPane.setBorder(BorderFactory.createEmptyBorder());
		splitPane.setResizeWeight(1.0);
		splitPane.setDividerSize(3);
		splitPane.setUI(new BasicSplitPaneUI() {
			@Override
			public BasicSplitPaneDivider createDefaultDivider() {
				return new BasicSplitPaneDivider(this) {
					public void setBorder(Border b) {}
					@Override
					public void paint(Graphics g) 
					{
						g.setColor(Color.decode(Palette.gray500));
						g.fillRect(0, 0, getSize().width, getSize().height);
						super.paint(g);
					}
				};
			}
		});
		splitPane.setBorder(null);
	      
		addLayout = new JButton(new FontIcon().of(BoxiconsRegular.ADD_TO_QUEUE, 15));
		setSizes(addLayout, 25, 25);
	      
		//
	      
		searchbox = new JTextField();
		//searchbox.setPreferredSize(new Dimension(100, 20));
		setSizes(searchbox, 100, 20);
	      
		model = new DefaultListModel<>();
		//model.addElement("foo");
		//model.addElement("foo");
		//model.addElement("foo");
	      
		list = new JList<Layouts>();
		list.setModel(model);
		list.setCellRenderer(new ThemedListCellRenderer(Color.decode(Palette.gray800), Color.decode(Palette.gray500), Color.decode(Palette.gray600), Color.decode(Palette.gray50)));
		list.setComponentPopupMenu(popup);
		
		popup = new JPopupMenu();
		popup.setUI(new ThemedPopupMenuUI(Color.decode(Palette.gray800)));
		popup.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		
		edit = new JMenuItem("Edit");
		edit.setPreferredSize(new Dimension(100, 25));
		edit.setUI(new ThemedMenuItem(Color.decode(Palette.gray600), Color.decode(Palette.gray50)));
		edit.setForeground(Color.decode(Palette.gray500));

		delete = new JMenuItem("Delete");
		delete.setPreferredSize(new Dimension(100, 25));
		delete.setUI(new ThemedMenuItem(Color.decode(Palette.gray600), Color.decode(Palette.gray50)));
		delete.setForeground(Color.decode(Palette.gray500));
		//
		popup.add(edit);
		popup.add(delete);
	      
		ThemedJScrollPane scrollPane = new ThemedJScrollPane(list);
		scrollPane.setVerticalScrollBarPolicy(ThemedJScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JPanel bar = new JPanel();
		bar.setLayout(new BoxLayout(bar, BoxLayout.X_AXIS));
		bar.setPreferredSize(new Dimension(this.getWidth(), 25));
		bar.add(new JLabel("Available Layouts"));
		bar.add(Box.createHorizontalGlue());
		bar.add(addLayout);
	      
		JPanel listContainer = new JPanel();
		listContainer.setLayout(new BorderLayout());
		listContainer.add(bar, BorderLayout.NORTH);
		listContainer.add(scrollPane, BorderLayout.CENTER);
		//
	      
		layoutName = new JLabel("Click on a layout to edit it");
		editLayout = new JButton(new FontIcon().of(BoxiconsRegular.EDIT, 15));
		setSizes(editLayout, 25, 25);
	      
		//
	      
		layoutPreview = new CenteredPanel();
		layoutPreview.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		layoutPreview.setImage(new ImageIcon(createPreview(0, 0, 1920, 1080)).getImage());
		layoutPreview.setBackground(Color.decode(Palette.gray800));
		setSizes(layoutPreview, 160, 90);
	      
		JPanel previewContainer = new JPanel();
		previewContainer.setLayout(new BorderLayout());
		previewContainer.setBorder(BorderFactory.createEmptyBorder());
		setSizes(previewContainer, 160, 90);
		previewContainer.setBackground(Color.BLACK);
		previewContainer.add(layoutPreview, BorderLayout.CENTER);
	      
	      
		//
		JPanel detailbar = new JPanel();
		detailbar.setLayout(new BoxLayout(detailbar, BoxLayout.X_AXIS));
		detailbar.setPreferredSize(new Dimension(this.getWidth(), 25));
		detailbar.add(layoutName);
		detailbar.add(Box.createHorizontalGlue());
		detailbar.add(editLayout);
		
		JPanel detailInfo = new JPanel();
		detailInfo.setLayout(new BorderLayout());
		detailInfo.setPreferredSize(new Dimension(100, 140));
		detailInfo.setBackground(Color.decode(Palette.gray800));
		
		JPanel detailContainer = new JPanel();
		detailContainer.setLayout(new BorderLayout());
		detailContainer.add(previewContainer, BorderLayout.CENTER);
		detailContainer.add(detailInfo, BorderLayout.SOUTH);
	     
	      
		//
		//
	      
		addLayout.addActionListener(new ActionListener() {
			String name = null;
			boolean isValid = false;
			@Override
			public void actionPerformed(ActionEvent e) {
				int shouldContinue = JOptionPane.showConfirmDialog(LayoutDialog.this, "Do you want to add a new Layout?");
		        if(shouldContinue == JOptionPane.YES_OPTION) {
		        	DrawNewLayout draw = null;
		        	while(!isValid) {
		        		name = JOptionPane.showInputDialog("Enter layout name (No spaces or numbers)");
		        		if(name != null && name.matches("[A-Za-z]+")) {
		        			 isValid = true;
		        			 draw = new DrawNewLayout(parent, LayoutDialog.this, name, new DrawNewLayoutCallback() {
								@Override
								public void onLayoutAdded(Layouts newLayout) {
					        		if(newLayout != null) {
					        			Window.config.writeNewLayout(newLayout);
						        		Window.updateMode();
						        		updateList();
						        		//
						        		Window.getLogger().logS("Added layout: " + name + "\n");
					        		}
								}
							});
		        		} else {
		        			JOptionPane.showMessageDialog(null, "Name is invalid, try again");
		        		}
		        	}
		        } 
			}
		});
		
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//Window.print(list.getSelectedIndex());
				int i = list.getSelectedIndex();
				layoutName.setText(layoutData.get(i).getName());
				if(i == 0) {
					layoutPreview.setImage(new ImageIcon(createPreview(0, 0, 1920, 1080)).getImage());
				} else if (i == 1) {
					layoutPreview.setImage(new ImageIcon(createPreview(0, 0, 1920, 1080)).getImage());
				} else {
					String[] coords = layoutData.get(i).getCoords();
					int x1 = Integer.valueOf(coords[0]);
					int y1 = Integer.valueOf(coords[1]);
					int x2 = Integer.valueOf(coords[2]);
					int y2 = Integer.valueOf(coords[3]);
		    		  
					layoutPreview.setImage(new ImageIcon(createPreview(x1, y1, x2, y2)).getImage());
					//layoutPreview.setBackground(Color.RED);
				}		  
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseReleased(MouseEvent e) {
		        if (e.isPopupTrigger()) {
		            int index = list.locationToIndex(e.getPoint());
		            if (index >= 0 && list.getCellBounds(index, index).contains(e.getPoint())) {
		            	//selected = index;
		            	list.setSelectedIndex(index);
		            	popup.show(list, e.getX(), e.getY());
		            }
		        }		        
		    }
		});
		
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editLayout.doClick();
			}
		});
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int prompt = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this layout?", "LayoutDialog", JOptionPane.YES_NO_OPTION);
			}
		});
		
		//
		//
		//
		
		editLayout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				new EditLayoutDialog(parent, true);
			}
		});
	      
		
	      
	      
	      
		//
		// DATA
		layoutData = Window.getLayouts();
		layoutData.forEach(layout -> {
			model.addElement(layout);
		});
		//
		//
	      
		layoutListPanel.add(searchbox, BorderLayout.NORTH);
		layoutListPanel.add(listContainer, BorderLayout.CENTER);
		//
		layoutSettingPanel.add(detailbar, BorderLayout.NORTH);
		layoutSettingPanel.add(detailContainer, BorderLayout.CENTER);
		//
	      
		//
		root.add(splitPane);
		
		//
		//
		setContentPane(root);
		setLocationRelativeTo(parent);	
		setVisible(true);
	}
	
	private void setSizes(JComponent comp, int w, int h) {
		comp.setPreferredSize(new Dimension(w, h));
		comp.setMaximumSize(new Dimension(w, h));
	}
	
	private Image createPreview(int x1, int y1, int x2, int y2) {
		BufferedImage image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = image.createGraphics();
	    graphics2D.setColor(Color.decode(Palette.gray900));
	    graphics2D.fillRect(0, 0, 1920, 1080);

	    // Draw the white border on the image
	    graphics2D.setColor(Color.decode(Palette.gray50));
	    graphics2D.setStroke(new BasicStroke(10)); // Adjust the border thickness as needed
	    graphics2D.drawRect(x1, y1, (x2 - x1), (y2 - y1));

	    // Create a scaled version of the image
	    Image scaledImage = image.getScaledInstance(160, 90, Image.SCALE_SMOOTH);
	    return scaledImage;
	}
	
	private void updateList() {
		ListSelectionListener l = list.getListSelectionListeners()[0];
		list.removeListSelectionListener(l);
		model.removeAllElements();
		//
		layoutData = Window.getLayouts();
		model.removeAllElements();
		//layoutData.addAll(Window.getLayouts());
		layoutData.forEach(layout -> {
			model.addElement(layout);
		});
		list.addListSelectionListener(l);
	}
}
