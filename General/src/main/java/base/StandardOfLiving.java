package base;

import java.io.Serializable;

public enum StandardOfLiving implements Serializable {

	ULTRA_HIGH("Очень высокий"),
	HIGH("Высокий"),
	ULTRA_LOW("Очень низкий");

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
