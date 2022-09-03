package base;

import java.io.Serializable;
import java.util.ResourceBundle;

public enum StandardOfLiving implements Serializable {

	ULTRA_HIGH(ResourceBundle.getBundle("enums").getString("ultraHigh")),
	HIGH(ResourceBundle.getBundle("enums").getString("high")),
	ULTRA_LOW(ResourceBundle.getBundle("enums").getString("ultraLow"));

	private final String title;

	StandardOfLiving(String title){
		this.title = title;
	}

	@Override
	public String toString() { return title;}

	public static StandardOfLiving fromString(String standardStr){
		for (StandardOfLiving standard : StandardOfLiving.values()){
			if (standard.toString().equalsIgnoreCase(standardStr)) return standard;
		}
		return null;
	}

}
