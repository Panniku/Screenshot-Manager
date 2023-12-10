package window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.text.StyledDocument;

import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.swing.FontIcon;

import constructors.Layouts;
import data.ConfigHandler;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import renderer.CenteredPanel;
import renderer.LogPanel;
import renderer.SelectionSnap;
import renderer.themed.ThemedJComboBox;
import renderer.themed.ThemedJScrollPane;
import themes.Palette;
import utils.CopyImagetoClipBoard;
import utils.Logger;
import window.dialog.WindowSettings;
import window.dialog.layout.EditLayoutDialog;
import window.dialog.layout.LayoutDialog;

// C:\\Users\\Panniku\\Pictures\\Screenshotter\\Snap_17062023_171751.png
//C:\Users\Panniku\Pictures\Screenshotter\Snap_23062023_171729.png
//C:\Users\Panniku\Pictures\Screenshotter\Snap_24062023_192018.png
public class Window {
	//
	public static JFrame window;
	// 1st
	JToolBar toolbar;
	// toolbar items
	JButton snap;
	static JComboBox<Layouts> layoutSelector;
	JButton editLayouts;
	JTextField coordinates;
	JToggleButton hide;
	JToggleButton pin;
	JButton settings;
	// 2nd
	JSplitPane splitPane;
	JPanel textLogger, snapHistory;
	//
	JToolBar textbar, imgbar;
	//
	StyledDocument doc;
	JTextPane textPane;
	ThemedJScrollPane textLog;
	static JScrollPane imgLog;
	//
	public static JPanel prvwPanel;
	JToggleButton togglePrvw;
	public static CenteredPanel prvwimg;
	//
	public static JLabel imgCount;
	//
	static JPanel logPanel;
	//JTable snapTable;
	//
	//DefaultTableModel tableModel;
	// Data
	public static ConfigHandler config;
	static String imgPath;
	File configFile;
	static ArrayList<Layouts> layoutData;
	//
	static Logger logger;
	public static GlobalKeyboardHook keyboardHook;
	//
	static int item;
	static int pos=0;
	public static boolean shouldHide=true;
	boolean isPinned=false;
	boolean isPreview=false;
	public static String temppath;
	static int count = 0;
	static boolean userClicked=false;
	static boolean consumed = true;
	
	Window() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
	        UIManager.put("ToggleButton.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
	        UIManager.put("CheckBox.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
	        UIManager.put("TabbedPane.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
	        UIManager.put("RadioButton.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
	        UIManager.put("Slider.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
	        UIManager.put("ComboBox.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
//	        //
//	        UIManager.put("TextArea.caretForeground", new ColorUIResource(Color.decode(Palette.gray50)));
//	        UIManager.put("TextPane.caretForeground", new ColorUIResource(Color.decode(Palette.gray50)));
//	        UIManager.put("TextField.caretForeground", new ColorUIResource(Color.decode(Palette.gray50)));
	        //UIManager.put("ComboBox.buttonBackground", new ColorUIResource(new Color(0, 255, 0)));
	        //UIManager.put("ComboBox.background", new ColorUIResource(UIManager.getColor("TextField.background")));
	        //UIManager.put("ComboBox.foreground", new ColorUIResource(UIManager.getColor("TextField.foreground")));
	        //UIManager.put("ComboBox.selectionBackground", new ColorUIResource(Color.decode(Palette.gray800)));
	        //UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.decode(Palette.gray500)));
	        
//	        UIManager.put("ScrollBar.thumb", new ColorUIResource(new Color(255, 255, 255)));
//	        UIManager.put("ScrollBar.thumbDarkShadow", new ColorUIResource(Color.RED));
//	        UIManager.put("ScrollBar.thumbHighlight", new ColorUIResource(Color.RED));
//	        UIManager.put("ScrollBar.thumbShadow", new ColorUIResource(Color.RED));
//	        //
//	        UIManager.put("ScrollBar.track", new ColorUIResource(Color.GREEN));
//	        UIManager.put("ScrollBar.trackForeground", new ColorUIResource(Color.GREEN));
//	        UIManager.put("ScrollBar.trackHighlight", new ColorUIResource(Color.GREEN));
//	        UIManager.put("ScrollBar.trackHighlightForeground", new ColorUIResource(Color.GREEN));
	        //
	        
		} catch (Exception e1) {
			e1.printStackTrace();
		}	
		
		FontIcon windowIcon = new FontIcon().of(BoxiconsRegular.IMAGE, 128, Color.decode(Palette.gray500));
		ImageIcon image = windowIcon.toImageIcon();
		Image ic = image.getImage();
		window = new JFrame("Screenshot Manager v0.28");
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(600, 350);
		//window.setFocusable(false);
		window.setMinimumSize(new Dimension(300, 200));
		window.setResizable(true);
		window.setAlwaysOnTop(false);
		window.setIconImage(ic);
		
		toolbar = new JToolBar();
		//toolbar.setLayout(new FlowLayout(0, 0, 0));
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS));
		toolbar.setFloatable(false);
		toolbar.setRollover(true);
		toolbar.setPreferredSize(new Dimension(window.getWidth(), 25));
		//toolbar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		//toolbar.setAlignmentX(SwingConstants.LEFT);
		toolbar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.decode(Palette.gray500)));
		toolbar.setBackground(Color.decode(Palette.gray800));
		
		snap = new JButton(new FontIcon().of(BoxiconsRegular.IMAGE_ADD, 15, Color.decode("#424242")));
		snap.setToolTipText("<html>Shortcut: \\<br>Freeform: Selection based screenshot<br>Fullscreen: Screenshot whole display<br>Other layouts: based on coordinates</html>");	
		snap.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		snap.setSelectedIcon(new FontIcon().of(BoxiconsRegular.IMAGE_ADD, 15, new Color(68, 110, 158)));
		snap.setPreferredSize(new Dimension(20, 20));
		snap.setBackground(Color.decode(Palette.gray800));
		
		layoutSelector = new ThemedJComboBox();
		layoutSelector.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		layoutSelector.setToolTipText("<html>Select screenshot layout/cropping mode.</html>");	
		layoutSelector.setPreferredSize(new Dimension(100, 20));
		layoutSelector.setMaximumSize(new Dimension(100, 20));
		layoutSelector.setOpaque(false);

		
		coordinates = new JTextField();
		coordinates.setToolTipText("<html>Displays the coordinates (in numbers) of a rectangle for the layout.</html>");	
		coordinates.setMargin(new Insets(0, 5, 0, 0));
		coordinates.setPreferredSize(new Dimension(100, 20));
		coordinates.setMaximumSize(new Dimension(100, 20));
		coordinates.setEditable(false);
		coordinates.setHorizontalAlignment(SwingConstants.LEADING);
		coordinates.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		coordinates.setForeground(Color.decode(Palette.gray500));
		coordinates.setBackground(Color.decode(Palette.gray800));
		
		
		editLayouts = new JButton(new FontIcon().of(BoxiconsRegular.EDIT, 15, Color.decode(Palette.gray500)));
		editLayouts.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		editLayouts.setPreferredSize(new Dimension(20, 20));
		editLayouts.setBackground(Color.decode(Palette.gray800));
		
		hide = new JToggleButton(new FontIcon().of(BoxiconsRegular.HIDE, 15, Color.decode(Palette.gray500)));
		hide.setToolTipText("<html>Toggle On: Hides the window whenever taking a screenshot.<br>Toggle Off: Window is visible when screenshotting.</html>");	
		hide.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		hide.setPreferredSize(new Dimension(20, 20));
		hide.setSelectedIcon(new FontIcon().of(BoxiconsRegular.HIDE, 15, Color.decode(Palette.gray50)));
		hide.setBackground(Color.decode(Palette.gray800));
		if(shouldHide) hide.setSelected(true);
		
		pin = new JToggleButton(new FontIcon().of(BoxiconsRegular.PIN, 15, Color.decode(Palette.gray500)));
		pin.setToolTipText("<html>Toggle On: Window is kept on top.<br>Toggle Off: Window loses focus.</html>");	
		pin.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		pin.setPreferredSize(new Dimension(20, 20));
		pin.setSelectedIcon(new FontIcon().of(BoxiconsRegular.PIN, 15,Color.decode(Palette.gray50)));
		pin.setBackground(Color.decode(Palette.gray800));
		
		settings = new JButton(new FontIcon().of(BoxiconsRegular.WRENCH, 15, Color.decode(Palette.gray500)));
		settings.setToolTipText("<html>Settings.</html>");	
		settings.setBorder(BorderFactory.createLineBorder(Color.decode(Palette.gray500)));
		settings.setPreferredSize(new Dimension(20, 20));
		settings.setBackground(Color.decode(Palette.gray800));
		
		///
		
		textLogger = new JPanel(new BorderLayout());
		textLogger.setPreferredSize(new Dimension(window.getWidth() / 2, 100));
		textLogger.setMinimumSize(new Dimension((window.getWidth() / 3), 100));
		
		snapHistory = new JPanel(new BorderLayout());
		snapHistory.setPreferredSize(new Dimension(window.getWidth() / 2, 1000));
		snapHistory.setMinimumSize(new Dimension((window.getWidth() / 3)+2, 100));
		
		textbar = new JToolBar();
		textbar.setLayout(new BoxLayout(textbar, BoxLayout.X_AXIS));
		textbar.setPreferredSize(new Dimension(window.getWidth(), 25));
		textbar.setFloatable(false);
		textbar.setRollover(true);
		textbar.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.decode(Palette.gray500)));
		textbar.setBackground(Color.decode(Palette.gray800));
		
		JLabel txtlb = new JLabel();
		txtlb.setForeground(Color.decode(Palette.gray500));
		txtlb.setIcon(new FontIcon().of(BoxiconsRegular.DETAIL, 15, Color.decode(Palette.gray500)));
		txtlb.setText("Logger");
		txtlb.setPreferredSize(new Dimension(50, 25));
		
		togglePrvw = new JToggleButton(new FontIcon().of(BoxiconsRegular.IMAGE_ALT, 15, Color.decode(Palette.gray500)));
		togglePrvw.setToolTipText("<html>Toggle image preview</html>");	
		togglePrvw.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		togglePrvw.setSelectedIcon(new FontIcon().of(BoxiconsRegular.IMAGE_ALT, 15, Color.decode(Palette.gray50)));
		togglePrvw.setPreferredSize(new Dimension(20, 20));
		togglePrvw.setBackground(Color.decode(Palette.gray800));
		
		imgbar = new JToolBar();
		imgbar.setLayout(new BoxLayout(imgbar, BoxLayout.X_AXIS));
		imgbar.setPreferredSize(new Dimension(window.getWidth(), 25));
		imgbar.setFloatable(false);
		imgbar.setRollover(true);
		imgbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.decode(Palette.gray500)));
		imgbar.setBackground(Color.decode(Palette.gray800));
		
		JLabel imglb = new JLabel();
		imglb.setForeground(Color.decode(Palette.gray500));
		imglb.setIcon(new FontIcon().of(BoxiconsRegular.IMAGES, 15, Color.decode(Palette.gray500)));
		imglb.setText("Image Log");
		imglb.setPreferredSize(new Dimension(50, 25));
		
//		JPanel p1 = new JPanel();
//		p1.setBackground(Color.RED);
//		JPanel p2 = new JPanel();
//		p1.setBackground(Color.BLUE);
//		JPanel p3 = new JPanel();
//		p1.setBackground(Color.GREEN);
//
//		
//		
//		JPanel[] panels = {p1, p2, p3};
		
		
		//snapList.setListData(panels);
		//snapList.add(new JLabel());)
		//snapList.ensureIndexIsVisible(50);
		
		//splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, textLogger, snapHistory);
		splitPane.setContinuousLayout(true);
		splitPane.setDividerLocation(window.getWidth() /2);
		//splitPane.setOneTouchExpandable(true);
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
		
		textPane = new JTextPane();
		textPane.setBackground(Color.decode(Palette.gray900));
		textPane.setPreferredSize(new Dimension(textLogger.getWidth() + 100, 100));
		textPane.setFont(new Font("Consolas", Font.PLAIN, 12));
		textPane.setEditable(false);
		//print("T:  " + textPane.isEditable());
		
		
		
		//doc = textPane.getStyledDocument();
		//Style success = textPane.addStyle("Success", null);
		//StyleConstants.setForeground(success, Color.GREEN);
		
		//print(snap.getUI().toString());
		
		//textLog = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		textLog = new ThemedJScrollPane(textPane);
		textLog.setBorder(BorderFactory.createEmptyBorder());
		textLog.setBackground(Color.decode(Palette.gray800));
		//textLog.getVerticalScrollBar().setPreferredSize(new Dimension(15, textLog.getHeight()));
		//textLog.getHorizontalScrollBar().setPreferredSize(new Dimension(textLog.getWidth(), 15));
		//textLog.getVerticalScrollBar().setBackground(new ColorUIResource(Color.RED));
		
		prvwPanel = new JPanel();
		prvwPanel.setBorder(BorderFactory.createEmptyBorder());
		prvwPanel.setLayout(new BorderLayout());
		prvwPanel.setPreferredSize(new Dimension(textLogger.getWidth(), textLogger.getHeight()));
		prvwPanel.setBackground(Color.decode(Palette.gray900));
		
		prvwimg = new CenteredPanel();
		prvwimg.setBackground(Color.decode(Palette.gray900));
		prvwimg.setPreferredSize(new Dimension(prvwPanel.getWidth(), prvwPanel.getHeight()));
		//prvwPanel.setVisible(false);
		//prvwPanel.setOpaque(false);
		
		//print(String.valueOf(textLog.getVerticalScrollBar().getUnitIncrement()));
		
		imgCount = new JLabel();
		imgCount.setText("Snaps taken: " + count);
		imgCount.setForeground(Color.decode(Palette.gray500));
		imgCount.setPreferredSize(new Dimension(100, 20));		
		//
		logger = new Logger(textPane, textLog);
		
		logger.logA("Running..\n");
		logger.logV("Screenshot Manager Private Test\n\n");
		logger.logW("JDK: ");
		logger.logV(System.getProperty("java.version") + "\n");
		logger.logW("JRE: ");
		logger.logV(System.getProperty("java.runtime.version") + "\n");
		logger.logA("------------------------------------------------------------------------------\n");
		
		//
		
		logPanel = new JPanel();
		logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));
		logPanel.setBorder(BorderFactory.createEmptyBorder());
		logPanel.setBackground(Color.decode(Palette.gray900));
		//logPanel.setPreferredSize(new Dimension(200, 200));
		
		//imgLog = new JScrollPane(logPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		imgLog = new ThemedJScrollPane(logPanel);
		imgLog.setBackground(Color.decode(Palette.gray800));
		imgLog.setBorder(BorderFactory.createEmptyBorder());
		imgLog.getVerticalScrollBar().setUnitIncrement(20);
		imgLog.setVerticalScrollBarPolicy(ThemedJScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		imgLog.setHorizontalScrollBarPolicy(ThemedJScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//print(String.valueOf(imgLog.getVerticalScrollBar().getUnitIncrement()));
		
		// Events
		
//		window.addComponentListener(new ComponentAdapter() {
//			@Override
//			public void componentResized(ComponentEvent e) {
//				if(userClicked){
//					print("comp resize");
//				}
//			}
//		});
		
		window.addWindowStateListener(new WindowStateListener() {
			
			@Override
			public void windowStateChanged(WindowEvent e) {
				if((e.getNewState() & JFrame.ICONIFIED) == JFrame.ICONIFIED) {
					shouldHide = false;
				} else {
					shouldHide = true;
				}
			}
		});
		
		//
		
		snap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//print(String.valueOf(item));
				if(item == 0) {
					
					SelectionSnap snapper = new SelectionSnap();
				    
				} else if(item == 1) {
					Rectangle rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
					takeScreenShot((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
				} else {
					String[] coords = layoutData.get(item).getCoords();
					int x1 = Integer.valueOf(coords[0]);
					int y1 = Integer.valueOf(coords[1]);
					int x2 = Integer.valueOf(coords[2]);
					int y2 = Integer.valueOf(coords[3]);
					takeScreenShot(x1, y1, x2, y2);
				}
			}
		});
		
		layoutSelector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				item = layoutSelector.getSelectedIndex();
				print(String.valueOf(item));
				logger.logA("Selected: ");
				logger.logS(layoutSelector.getItemAt(item) + "\n");
				if(item == 0) { // Free form
					coordinates.setText("Freeform selection");
					snap.setIcon(new FontIcon().of(BoxiconsRegular.SELECTION, 15, Color.decode(Palette.gray500)));
					//snap.setSelectedIcon(new FontIcon().of(BoxiconsRegular.SELECTION, 15, new Color(68, 110, 158)));
				} else if(item == 1) { // Full screen
					coordinates.setText("Entire display");
					snap.setIcon(new FontIcon().of(BoxiconsRegular.WINDOW, 15,Color.decode(Palette.gray500)));
					//snap.setSelectedIcon(new FontIcon().of(BoxiconsRegular.WINDOW, 15, new Color(68, 110, 158)));
				} else {
					snap.setIcon(new FontIcon().of(BoxiconsRegular.IMAGE_ADD, 15, Color.decode(Palette.gray500)));
					//snap.setSelectedIcon(new FontIcon().of(BoxiconsRegular.IMAGE_ADD, 15, new Color(68, 110, 158)));
					print("i hate you: " + layoutData);
					String[] coords = layoutData.get(item).getCoords();
					coordinates.setText(coords[0]+","+coords[1]+","+coords[2]+","+coords[3]);
				}
			}
		});
		
		editLayouts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//window.setVisible(false);
				//print("hook pre: " + keyboardHook.isAlive());
				keyboardHook.shutdownHook();
				//print("hook post : " + keyboardHook.isAlive());
				LayoutDialog dialog = new LayoutDialog(window);
				
				//JOptionPane.showInputDialog("");
				//window.setVisible(true);
				//print("hook t pre: " + keyboardHook.isAlive());
				keyboardHook = new GlobalKeyboardHook(true);
				//print("hook t post : " + keyboardHook.isAlive());
				startKeyHook();
			}
		});
		
		hide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!shouldHide) {
					shouldHide = true;
					logger.logV("shouldHide: true\n");
				} else {
					shouldHide = false;
					logger.logV("shouldHide: false\n");
				}
			}
		});
		
		pin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isPinned) {
					isPinned = true;
					window.setAlwaysOnTop(true);
					logger.logV("isPinned: true\n");
					
				} else {
					isPinned = false;
					window.setAlwaysOnTop(false);
					logger.logV("shouldHide: false\n");
				}
			}
		});
		
		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new WindowSettings();
			}
		});
		
		///////////////////////////////////////////
		
		
		togglePrvw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isPreview) {
					isPreview = false;
					textLogger.remove(prvwPanel);
					textLogger.add(textLog, BorderLayout.CENTER);
					
					textLogger.revalidate();
					textLogger.repaint();
					logger.logV("isPreview: true\n");
				} else {
					isPreview = true;
					textLogger.remove(textLog);
					textLogger.add(prvwPanel, BorderLayout.CENTER);
					
					prvwPanel.add(prvwimg, BorderLayout.CENTER);
					
					textLogger.revalidate();
					textLogger.repaint();
					logger.logV("isPreview: false\n");
				}
			}
		});
		
		
		prvwPanel.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				if(temppath != null) {
					setPreviewImage(temppath);
				}
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		///////////////////////////////////////////
		
		//logger.logV("log test before config");
		
		// Data handling
		config = new ConfigHandler();

		imgPath = config.getImgPath();
		configFile = config.getConfig();
		//
		config.readConfig();
		config.readLayoutFile();
		config.readLayouts();
	
		
		layoutData = config.getLayouts();
		//String[] ff = {"0", "0", "0", "0"};
		//String[] fs = {"0", "0", "0", "0"};
		layoutData.add(0, new Layouts("Freeform", null));
		layoutData.add(1, new Layouts("Fullscreen", null));
		layoutData.forEach(layout -> {
			//print(layout.toString());
			layoutSelector.addItem(layout);
		});
		//print(String.valueOf(layoutEnd));
		//Layouts add = new Layouts("Add new", null);
		//mode.addItem(add);
		//layoutData.add(layoutEnd, add);
		//layoutEnd++;
		
		
		
		
		//
		
		//
		//
		//
		toolbar.addSeparator();
		toolbar.add(snap);
		toolbar.addSeparator();
		toolbar.add(layoutSelector);
		toolbar.addSeparator();
		toolbar.add(coordinates);
		toolbar.add(editLayouts);
		toolbar.addSeparator();
		toolbar.add(Box.createHorizontalGlue());
		toolbar.add(hide);
		toolbar.add(pin);
		toolbar.addSeparator();
		toolbar.add(settings);
		toolbar.addSeparator();
		//		
		textLogger.add(textbar, BorderLayout.NORTH);
		textLogger.add(textLog, BorderLayout.CENTER);
		//textLogger.add(prvwPanel, BorderLayout.CENTER);
		//
		textbar.addSeparator();
		textbar.add(txtlb);
		textbar.addSeparator();
		textbar.add(togglePrvw);
		//
		imgbar.addSeparator();
		imgbar.add(imglb);
		imgbar.addSeparator();
		imgbar.add(imgCount);
		//imgbar.addSeparator();
		//
		snapHistory.add(imgbar, BorderLayout.NORTH);
		snapHistory.add(imgLog, BorderLayout.CENTER);
		//
		//splitPane.add(textLogger);
		//splitPane.add(snapHistory);
		//
		window.add(toolbar, BorderLayout.NORTH);
		window.add(splitPane);
		//
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		
		//logger.logV("log test");
		
		// Register JNativeHook
		try {
			logger.logA("[Java System Hook] Registering Global KeyEvents...\n");
			//GlobalScreen.registerNativeHook();
			logger.logS("[Java System Hook] Registered Global KeyEvents\n");
			//print(String.valueOf(GlobalScreen.isNativeHookRegistered()));
			
			keyboardHook = new GlobalKeyboardHook(true);
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    	logger.logE("[Java System Hook] Failed to register Global KeyEvents");
	    }

		// Flag to track whether the event has been consumed
		startKeyHook();

	}


	
	
	
	// main run func
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Window window = new Window();
				//new WindowSettings(); ///////////////////////// TODO REMOVE THIS CODE WHEN SETTINGS IS COMPLETED
				
				//JOptionPane.showInputDialog("");
				//new LayoutDialog(null);
			}
		});
	}
	
	public void startKeyHook() {
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
		    @Override
		    public void keyPressed(GlobalKeyEvent event) {
		    	if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_OEM_5) {
	                snap.doClick();
	                consumed = true;
	            }
		    }

		    @Override
		    public void keyReleased(GlobalKeyEvent event) {
		        consumed = false;
		    }
		});
	}
	
	public static void takeScreenShot(int x1, int y1, int x2, int y2) {
		try {
			Robot r = new Robot();
			//print("x1: " + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2);

			Rectangle capture = new Rectangle(x1, y1, (x2 - x1), (y2 - y1));	
			BufferedImage img = null;
			if(shouldHide) {
				userClicked = false;
				window.setState(JFrame.ICONIFIED);
				img = r.createScreenCapture(capture);
				window.setState(JFrame.NORMAL);
			} else {
				img = r.createScreenCapture(capture);
			}
		
			saveScreenShot(img);
		} catch (Exception e) {
			e.printStackTrace();
			logger.logW("Failed to take screenshot\n");
		}
	}
	
	public static void saveScreenShot(Image img) {
		try {
			String path = imgPath + "\\Snap_" + getDateTime() + ".png";
			ImageIO.write((RenderedImage) img, "png", new File(path));
			logger.logS("Saved to ");
			logger.logA(path + "\n");
			CopyImagetoClipBoard ci = new CopyImagetoClipBoard();
			ci.copyImage((BufferedImage) img);
			
			//
			//Image newImage = img.getScaledInstance(160, 90, Image.SCALE_DEFAULT);
			Dimension dimen = getScaledDimension(new Dimension(img.getWidth(null), img.getHeight(null)), new Dimension(160, 90));
			Image scaled = img.getScaledInstance((int)dimen.getWidth(), (int)dimen.getHeight(), Image.SCALE_SMOOTH);
			addToLog(scaled, getFormattedDateTime() + " | " + img.getWidth(null) + "x" + img.getHeight(null), path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addToLog(Image image, String info, String path) {
        LogPanel logger = new LogPanel();
        logger.setImage(new ImageIcon(image));
        logger.setText(info);
        logger.setPath(path);

        count++;
        imgCount.setText("Snaps taken: " + count);
        
//        if(logPanel.countComponents() >= 150) {
//        	logPanel.remove(logPanel.getComponent(0));
//        	Window.logger.logW("Cleared image logger to be within 150 items.\n");
//        }
        logPanel.add(logger);
        
        setPreviewImage(path);
        logPanel.revalidate();
        logPanel.repaint();
    }
	
	public static void updateMode() {
		ActionListener l = layoutSelector.getActionListeners()[0];
		layoutSelector.removeActionListener(l);
		//
		//print("c:" + config.getLayouts());
		layoutData = config.getLayouts();
		//print("l: " + layoutData);
		//String[] ff = {"0", "0", "0", "0"};
		//String[] fs = {"0", "0", "0", "0"};
		layoutData.add(0, new Layouts("Freeform", null));
		layoutData.add(1, new Layouts("Fullscreen", null));
		//print("l p : " + layoutData);
		layoutSelector.removeAllItems();
		layoutData.forEach(layout -> {
			print(layout);
			layoutSelector.addItem(layout);
		});
		//print("s: " + layoutData.size());
		layoutSelector.addActionListener(l);
	}
	
	//
	//
	//
	//
	//
	
	//
	public static Rectangle getMaximumScreenBounds() {
	    int minx=0, miny=0, maxx=0, maxy=0;
	    GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    for(GraphicsDevice device : environment.getScreenDevices()){
	        Rectangle bounds = device.getDefaultConfiguration().getBounds();
	        minx = Math.min(minx, bounds.x);
	        miny = Math.min(miny, bounds.y);
	        maxx = Math.max(maxx,  bounds.x+bounds.width);
	        maxy = Math.max(maxy, bounds.y+bounds.height);
	    }
	    return new Rectangle(minx, miny, maxx-minx, maxy-miny);
	}
	
	private static Dimension getScaledDimension(Dimension imageSize, Dimension boundary) {

        double widthRatio = boundary.getWidth() / imageSize.getWidth();
        double heightRatio = boundary.getHeight() / imageSize.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        return new Dimension((int) (imageSize.width  * ratio),
                             (int) (imageSize.height * ratio));
    }
	
	//
	public static String getDateTime() {
		LocalDate dateObj = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		String date = dateObj.format(formatter);
		
		Format f = new SimpleDateFormat("HHmmss");
		String strResult = f.format(new Date());
		
		String datetime = date + "_" + strResult;
		return datetime;
	}
	
	public static String getFormattedDateTime() {
		LocalDate dateObj = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String date = dateObj.format(formatter);
		
		Format f = new SimpleDateFormat("HH:mm:ss");
		String strResult = f.format(new Date());
		
		String datetime = date + " " + strResult;
		return datetime;
	}
	
	//
	
	public static Logger getLogger() {
		return logger;
	}
	
	public static JFrame getParent() {
		return window;
	}
	
	public static ArrayList<Layouts> getLayouts(){
		return layoutData;
	}
	
	public static void setPreviewImage(String path) {
		try {
			ImageIcon ic = new ImageIcon(ImageIO.read(new File(path)));
	    	Dimension scaledDimen = getScaledDimension(new Dimension(ic.getImage().getWidth(null), ic.getImage().getHeight(null)), new Dimension(prvwimg.getWidth(), prvwimg.getHeight()));
	    	
			if(scaledDimen.getWidth() !=0 && scaledDimen.getHeight() != 0) {
				Image scaled = ic.getImage().getScaledInstance((int)scaledDimen.getWidth(), (int)scaledDimen.getHeight(), Image.SCALE_SMOOTH);
				prvwimg.setImage(scaled);
				temppath = path;
			}

			prvwimg.revalidate();
			prvwimg.repaint();
			imgLog.getVerticalScrollBar().setValue(imgLog.getVerticalScrollBar().getMaximum());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//
	//
	// 
	
	public static void print(Object string) {
		System.out.println(string);
	}

	
//	public static void log(String string) {
//		textPane.setText(textPane.getText() + string + "\n");
//	}
	
}
