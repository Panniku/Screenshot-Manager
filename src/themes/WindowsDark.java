package themes;

import com.formdev.flatlaf.FlatDarkLaf;

public class WindowsDark extends FlatDarkLaf {
	
	public static boolean setup() {
        return setup( new WindowsDark() );
    }

    @Override
    public String getName() {
        return "WindowsDark";
    }
    
}
