package window.dialog.layout;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import javax.swing.JPanel;
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
	//
	JLabel layoutName;
	JButton editLayout;
	//JPanel previewContainer;
	CenteredPanel layoutPreview;
	
	//
	ArrayList<Layouts> layoutData;
	
	public LayoutDialog(JFrame parent) {
		super(parent, "Layouts", true);
		  setSize(400, 300);
	      setResizable(false);
	      setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);	      
	      
	      root = new JPanel();
	      root.setLayout(new BorderLayout());
	      
	      
	      
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
	      
	      DefaultListModel model = new DefaultListModel<>();
	      //model.addElement("foo");
	      //model.addElement("foo");
	      //model.addElement("foo");
	      
	      list = new JList<Layouts>();
	      list.setModel(model);
	      
	      JPanel bar = new JPanel();
	      bar.setLayout(new BoxLayout(bar, BoxLayout.X_AXIS));
	      bar.setPreferredSize(new Dimension(this.getWidth(), 25));
	      bar.add(new JLabel("Available Layouts"));
	      bar.add(Box.createHorizontalGlue());
	      bar.add(addLayout);
	      
	      JPanel listContainer = new JPanel();
	      listContainer.setLayout(new BorderLayout());
	      listContainer.add(bar, BorderLayout.NORTH);
	      listContainer.add(list, BorderLayout.CENTER);
	      //
	      
	      layoutName = new JLabel("Click on a layout to edit it");
	      editLayout = new JButton(new FontIcon().of(BoxiconsRegular.EDIT, 15));
	      setSizes(editLayout, 25, 25);
	      
	      //
	      
	      layoutPreview = new CenteredPanel();
	      //layoutPreview.setPreferredSize(new Dimension());
	      setSizes(layoutPreview, 160, 90);
	      //layoutPreview.setBackground(Color.BLUE);
	      //layoutPreview.setImage(new ImageIcon("C:\\Users\\Panniku\\Pictures\\Screenshotter\\Snap_23062023_171729.png").getImage());
	      //layoutPreview.
	      
	      JPanel previewContainer = new JPanel();
	      //previewContainer.setLayout(new BorderLayout());
	      //previewContainer.setPreferredSize(new Dimension(160, 90));
	      setSizes(previewContainer, 160, 90);
	      //previewContainer.setBackground(Color.GREEN);
	      previewContainer.add(layoutPreview);
	     
	      
	      //
	      JPanel detailbar = new JPanel();
	      detailbar.setLayout(new BoxLayout(detailbar, BoxLayout.X_AXIS));
	      detailbar.setPreferredSize(new Dimension(this.getWidth(), 25));
	      detailbar.add(layoutName);
	      detailbar.add(Box.createHorizontalGlue());
	      detailbar.add(editLayout);
	      
	      JPanel detailContainer = new JPanel();
	      detailContainer.setLayout(new BorderLayout());
	      detailContainer.add(previewContainer, BorderLayout.CENTER);
	      
	      
	      //
	      //
	      
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
	
}
