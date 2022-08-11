package base;

import java.io.Serializable;

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


}
