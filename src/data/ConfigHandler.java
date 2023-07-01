package data;


import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

import constructors.Layouts;
import utils.Logger;
import window.Window;

public class ConfigHandler {
	
	String imgPath;
	File configFile;
	File layoutFile;
	
	ArrayList<String> configs = new ArrayList<>();
	ArrayList<String> layouts = new ArrayList<>();
	//
	ArrayList<Layouts> layoutArray = new ArrayList<Layouts>();
	//ArrayList<ImageIcon> layoutIcons;
	//ArrayList<String> layoutNames;
	//ArrayList<String[]> layoutCoords;
	
	Logger logger = Window.getLogger();
	
	public ConfigHandler() {
		File imgDir = new File(System.getProperty("user.home") + "/Pictures" + "/Screenshotter/");
		
		File configDir = new File(System.getenv("APPDATA") + "/Screenshotter/");
		configFile = new File(configDir + "/config.txt");
		
		File layoutDir = new File(System.getenv("APPDATA") + "/Screenshotter/");
		layoutFile = new File(layoutDir + "/layouts.txt");
		
		if(!imgDir.exists()) {
			imgDir.mkdir();
			logger.logA("Made Folder: ");
			logger.logS(imgDir.getAbsolutePath() + "\n");
		} 
		
		imgPath = imgDir.getAbsolutePath();
		logger.logA("img folder: ");
		logger.logS(imgDir.getAbsolutePath() + "\n");
		
		if(!configDir.exists()) {
			configDir.mkdir();
			logger.logA("Made new Folder: " + configDir.getAbsolutePath());
			logger.logS(configDir.getAbsolutePath() + "\n");
		}
		
		logger.logA("config folder: ");
		logger.logS(configDir.getAbsolutePath() + "\n");
		
		if(!layoutDir.exists()) {
			layoutDir.mkdir();
			logger.logA("Made Folder: ");
			logger.logS(layoutDir.getAbsolutePath() + "\n");
		}
		
		logger.logA("layouts folder: ");
		logger.logS(layoutDir.getAbsolutePath() + "\n");
		
		if(!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		logger.logA("config.txt: ");
		logger.logS(configFile.getAbsolutePath() + "\n");
		
		if(!layoutFile.exists()) {
			try {
				layoutFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		logger.logA("layouts.txt: ");
		logger.logS(layoutFile.getAbsolutePath() + "\n");
		//logger.logA("");
		
		logger.logA("------------------------------------------------------------------------------\n");
	}
	
	public void readConfig() {
		try {
			//File
			FileReader fr = new FileReader(configFile);
			BufferedReader reader = new BufferedReader(fr);
			String line = null;
			if(configFile.length() != 0) {
				while((line = reader.readLine()) != null) {
					configs.add(line);
				}
			} else {
				logger.logW("config.txt not setup.\n");
				logger.logA("Writing default config.txt\n");
				FileWriter writer = new FileWriter(configFile);
				writer.write("# Image Directory\nC:/Users/Panniku/Pictures/Screenshotter\n\n# Config File\nC:/Users/Panniku/AppData/Roaming/Screenshotter/config.txt");
				writer.close();
				
				while((line = reader.readLine()) != null) {
					configs.add(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readLayoutFile() {
		try {
			//File
			FileReader fr = new FileReader(layoutFile);
			BufferedReader reader = new BufferedReader(fr);
			String line = null;
			if(layoutFile.length() != 0) {
				layouts.clear();
				while((line = reader.readLine()) != null) {
					
					layouts.add(line);
					//Window.print(line);
				}
			} else {
				logger.logW("layouts.txt not setup.\n");
				logger.logA("Writing default layouts.txt\n");
				//FileWriter writer = new FileWriter(layoutFile);
				//writer.write(defaultScreenSize + "\nYoutube 83,203,1285,879\n");
				//writer.close();
				
				while((line = reader.readLine()) != null) {
					layouts.add(line);
					//Window.print(line);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////////////////////////////////
	
	public void readConfigs() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//
	public void readLayouts(){
		//Window.print(String.valueOf(layouts.size()));
		layoutArray.clear();
		layouts.forEach(layout -> {
			String[] split = layout.split("\\s+"); // [Layout] [0,0,0,0]
			String name = split[0];
			String[] coords = split[1].split(","); // [0][0][0][0]
			//Window.print(split + " // " + name + " // " + coords);

			layoutArray.add(new Layouts(name, coords)); // This initializes the Layouts class with every layout
			//Window.print( " size: "+String.valueOf(layoutArray.size()));
		});
	}
	
	public void writeNewLayout(Layouts layout) {
		try {
			String name = layout.getName();
			String x = layout.getCoords()[0];
			String y = layout.getCoords()[1];
			String w = layout.getCoords()[2];
			String h = layout.getCoords()[3];
			String coords = x + "," + y + "," + w + "," + h;
			String str = name + " " + coords;
			//
			FileWriter writer = new FileWriter(layoutFile, true);
			writer.write("\n");
			writer.write(str);
			writer.close();
			
			readLayoutFile();
			readLayouts();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.logE("Failed to append " + layout.getName() + ".\n");
		}
	}
	
	
//	public ImageIcon getLayoutIcon(int i) {
//		return null;
//	}
	
//	public String getLayoutName(int i) {
//		return layoutNames.get(i).get
//	}
	
	public ArrayList<Layouts> getLayouts(){
		return layoutArray;
	}
	
	public String getImgPath() {
		return imgPath;
	}
	
	public File getConfig() {
		return configFile;
	}
	
}
