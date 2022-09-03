package base;

import java.util.ResourceBundle;

public enum Leaders {
	MAXIM(ResourceBundle.getBundle("enums").getString("maxim")),
	MARTIN(ResourceBundle.getBundle("enums").getString("martin")),
	ANTONY(ResourceBundle.getBundle("enums").getString("antony")),
	ALINA(ResourceBundle.getBundle("enums").getString("alina")),
	ILIA(ResourceBundle.getBundle("enums").getString("ilia"));

	private final String title;

	Leaders(String title){
		this.title = title;
	}

	public static String getFromNum(int number){
		switch (number){
			case 0: return MAXIM.title;
			case 1: return MARTIN.title;
			case 2: return ANTONY.title;
			case 3: return ALINA.title;
			case 4: return ILIA.title;
			default: return MAXIM.title;
		}
	}

	@Override
	public String toString() { return title;}

	public static Leaders fromString(String leaderStr){
		for (Leaders leader : Leaders.values()){
			if (leader.toString().equalsIgnoreCase(leaderStr)) return leader;
		}
		return null;
	}
}
