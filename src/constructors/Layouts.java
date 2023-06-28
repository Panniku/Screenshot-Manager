package constructors;

import javax.swing.ImageIcon;

public class Layouts {

	private String name;
	private String[] coords;
	
	public Layouts(String name, String[] coords) {
		this.name = name;
		this.coords = coords;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCoords(String[] coords) {
		this.coords = coords;
	}
	
	public String[] getCoords() {
		return coords;
	}

	@Override
    public String toString() {
		return name;
	}
	
}
