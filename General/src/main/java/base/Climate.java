package base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum Climate implements Serializable {

	HUMIDCONTINENTAL("Влажный континентальный"),
	SUBARCTIC("Субарктический"),
	TUNDRA("Тундра");

	private final String title;

	Climate(String title){
		this.title = title;
	}

	@Override
	public String toString() { return title;}

	public static Climate fromString(String climateStr){
		for (Climate climate : Climate.values()){
			if (climate.toString().equalsIgnoreCase(climateStr)) return climate;
		}
		return null;
	}
}
