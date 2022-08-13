package base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

}
