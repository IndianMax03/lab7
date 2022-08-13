package input;

import base.*;

public class Creator {

	public static City createCity(){
		Typer typer = new Typer();

		String name = typer.typeName();

		Coordinates coordinates = typer.typeCoordinates();

		float area = typer.typeArea();

		int population = typer.typePopulation();

		float masl = typer.typeMetersAboveSeaLevel();

		Climate climate = typer.typeClimate();

		Government government = typer.typeGovernment();

		StandardOfLiving standardOfLiving = typer.typeStandardOfLiving();

		Human governor = typer.typeHuman();

		return new City(name, coordinates, area, population, masl, climate, government, standardOfLiving,
				governor);
	}

	//  todo для файлика из массивчика

}
