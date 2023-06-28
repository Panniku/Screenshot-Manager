package constructors;

public class Images {

	private String path;
	private String info;
	
	public Images(String path, String info) {
		this.path = path;
		this.info = info;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	
	@Override
	public String toString() {
	    return path;
	}
}
