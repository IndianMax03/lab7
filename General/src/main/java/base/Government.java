package base;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public enum Government {

	ANARCHY(ResourceBundle.getBundle("enums").getString("anarchy")),
	DICTATORSHIP(ResourceBundle.getBundle("enums").getString("dictatorship")),
	THALASSOCRACY(ResourceBundle.getBundle("enums").getString("thalassocracy")),
	THEOCRACY(ResourceBundle.getBundle("enums").getString("theocracy"));

	private final String title;

	Government(String title){
		this.title = title;
	}

	public static List<String> asLowerCaseStringList(){
		List<String> governmentValues = new ArrayList<>();
		for (Government government : values()){
			governmentValues.add(government.title.toLowerCase());
		}
		return governmentValues;
	}

	@Override
	public String toString() { return title;}

	public static Government fromString(String governmentStr){
		for (Government government : Government.values()){
			if (government.toString().equalsIgnoreCase(governmentStr)) return government;
		}
		return null;
	}
}
