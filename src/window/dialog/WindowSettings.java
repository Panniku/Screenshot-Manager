package window.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import window.Window;

public class WindowSettings extends JDialog {

	JPanel root;
	JTabbedPane tabPane;
	//
	JPanel generalPanel, appearancePanel, advancedPanel, aboutPanel;
	//
	JPanel appConfigPanel;
	JPanel screenshotPanel;
	////////////////////////////////////////////////////
	JComboBox<String> lang; // language
	JCheckBox autostart; // autostart
	JCheckBox closeTray; // closing minimizes app to tray
	JCheckBox notifs; // enable notifs
	JCheckBox checkUpdates; // check updates
	////////////////////////////////////////////////////
	JComboBox<String> imageformat;
	JTextField imagedir;
	JTextField capturedelay;
	JTextField fileformat;
	JCheckBox cnpclip;
	////////////////////////////////////////////////////

	//
	JPanel buttonContainer;
	JPanel buttonPanel;
	JButton ok, cancel, apply;
	JTextField restart;
	//
	String[] langs = {"English", "JPN", "French"};
	String[] ext = {"PNG", "JPG"};

	public WindowSettings() {
		
		
		//Insets insets = UIManager.getInsets("TabbedPane.contentBorderInsets");
        //insets.top = -1;
        //UIManager.put("TabbedPane.contentBorderInsets", insets);
		
		
		setTitle("Window Settings");
		setSize(300, 400);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//
		root = new JPanel();
		root.setLayout(new BorderLayout());
		//root.setBackground(Color.decode("#212121"));
		//
		tabPane = new JTabbedPane();
		//tabPane

		generalPanel = new JPanel();
		//generalPanel.setBackground(Color.decode("#212121"));

		appearancePanel = new JPanel();
		//appearancePanel.setBackground(Color.decode("#212121"));

		advancedPanel = new JPanel();
		//advancedPanel.setBackground(Color.decode("#212121"));
		
		aboutPanel = new JPanel();
		//aboutPanel.setBackground(Color.decode("#212121"));

		//
		buttonContainer = new JPanel();
		buttonContainer.setBorder(BorderFactory.createEmptyBorder());
		buttonContainer.setLayout(new BorderLayout());
		//buttonContainer.setBackground(Color.decode("#212121"));

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

		restart = new JTextField("Restart to\napply changes!");
		restart.setForeground(Color.decode("#e19f3e"));
		restart.setPreferredSize(new Dimension(125, 25));
		restart.setEditable(false);
		
		
		ok = new JButton("OK");
		cancel = new JButton("Cancel");
		apply = new JButton("Apply");
		

		// General - App Settings

		appConfigPanel = new JPanel();
		appConfigPanel.setPreferredSize(new Dimension(280, 125));
		appConfigPanel.setMinimumSize(new Dimension(280, 125));
		appConfigPanel.setBorder(BorderFactory.createTitledBorder("App Settings"));
		appConfigPanel.setLayout(new BoxLayout(appConfigPanel, BoxLayout.Y_AXIS));
		appConfigPanel.setForeground(Color.WHITE);
		//appConfigPanel.setBackground(Color.decode("#212121"));
		//appConfigPanel.setBackground(Color.GRAY);
		
		screenshotPanel = new JPanel();
		screenshotPanel.setPreferredSize(new Dimension(280, 125));
		screenshotPanel.setMinimumSize(new Dimension(280, 125));
		screenshotPanel.setBorder(BorderFactory.createTitledBorder("Screenshot settings"));
		screenshotPanel.setLayout(new BoxLayout(screenshotPanel, BoxLayout.Y_AXIS));
		screenshotPanel.setForeground(Color.WHITE);
		//screenshotPanel.setBackground(Color.decode("#212121"));
		//appConfigPanel.setBackground(Color.GRAY);

		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		lang = new JComboBox<>(langs);
		lang.setPreferredSize(new Dimension(100, 20));

		autostart = new JCheckBox();
		autostart.setPreferredSize(new Dimension(25, 25));

		closeTray = new JCheckBox();
		closeTray.setPreferredSize(new Dimension(25, 25));

		notifs = new JCheckBox();
		notifs.setPreferredSize(new Dimension(25, 25));

		checkUpdates = new JCheckBox();
		checkUpdates.setPreferredSize(new Dimension(25, 25));

		////////////////////////////////////////////////////////////////////////////////////////////////////////////

		imageformat = new JComboBox<String>(ext);
		imageformat.setPreferredSize(new Dimension(100, 25));
		
		imagedir = new JTextField();
		imagedir.setPreferredSize(new Dimension(150, 25));
		
		capturedelay = new JTextField();
		capturedelay.setPreferredSize(new Dimension(50, 25));
		
		fileformat = new JTextField();
		fileformat.setPreferredSize(new Dimension(150, 25));
		
 		cnpclip = new JCheckBox();
 		cnpclip.setPreferredSize(new Dimension(25, 25));
		
 		////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		appConfigPanel.add(setLayoutToSetting(new JLabel("Language"), lang));
		appConfigPanel.add(setLayoutToSetting(new JLabel("Autostart on bootup"), autostart));
		appConfigPanel.add(setLayoutToSetting(new JLabel("Minimize app to tray"), closeTray));
		appConfigPanel.add(setLayoutToSetting(new JLabel("Enable notifications"), notifs));
		appConfigPanel.add(setLayoutToSetting(new JLabel("Check for updates"), checkUpdates));
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
		screenshotPanel.add(setLayoutToSetting(new JLabel("Image format"), imageformat));
		screenshotPanel.add(setLayoutToSetting(new JLabel("Output directory"), imagedir));
		screenshotPanel.add(setLayoutToSetting(new JLabel("Capture delay"), capturedelay));
		screenshotPanel.add(setLayoutToSetting(new JLabel("File format"), fileformat));
		screenshotPanel.add(setLayoutToSetting(new JLabel("Copy to clipboard"), cnpclip));
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// EVENTS

		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// dispose(); <-- UNCOMMENT THIS WHEN SETTINGS IS DONE
			}
		});
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//
			}
		});

		generalPanel.add(appConfigPanel);
		generalPanel.add(screenshotPanel);

		tabPane.addTab("General", generalPanel);
		tabPane.addTab("Appearance", appearancePanel);
		tabPane.addTab("Advanced", advancedPanel);
		tabPane.addTab("About", aboutPanel);
		//
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		buttonPanel.add(apply);
		//buttonContainer.add(restart, BorderLayout.WEST);
		buttonContainer.add(buttonPanel, BorderLayout.EAST);
		
		root.add(tabPane);
		root.add(buttonContainer, BorderLayout.SOUTH);
		setContentPane(root);
		setVisible(true);
		setLocationRelativeTo(Window.getParent());
	}
	
	private JComponent setLayoutToSetting(JComponent item1, JComponent item2) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setForeground(Color.WHITE);
		panel.setBorder(BorderFactory.createEmptyBorder());
		//panel.setBackground(Color.decode("#212121"));
		setSizes(panel, 280, 20);
		panel.add(item1, BorderLayout.WEST);
		panel.add(item2, BorderLayout.EAST);
		return panel;
	}
	
	private void setSizes(JComponent comp, int w, int h) {
		comp.setPreferredSize(new Dimension(w, h));
		comp.setMaximumSize(new Dimension(w, h));
	}
}
