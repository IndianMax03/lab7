package input;

import base.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class Validator {

	private Validator(){}

	public static Long validateId(String arg){
		long id;
		try {
			id = Long.parseLong(arg);
			if (id <= 0) throw new NumberFormatException();
		} catch (NumberFormatException e){
			return null;
		}
		return id;
	}

	public static String validateName(String arg){
		if (arg.isEmpty()){
			return null;
		} else {
			return arg;
		}
	}

	public static Coordinates validateCoordinates(String arg){
		String[] coordinates = arg.trim().split(";");

		if (coordinates.length != 2){
			return null;
		}

		String xString = coordinates[0];
		String yString = coordinates[1];
		double x, y;

		try {
			x = Double.parseDouble(xString);
			y = Double.parseDouble(yString);
			if (y <= -628){
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e){
			return null;
		}
		return new Coordinates(x, y);
	}

	public static Float validateArea(String arg){
		float area;
		try{
			area = Float.parseFloat(arg);
			if (area <= 0){
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e){
			return null;
		}
		return area;
	}

	public static Integer validatePopulation(String arg){
		int population;
		try{
			population = Integer.parseInt(arg);
			if (population <= 0){
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e){
			return null;
		}
		return population;
	}

	public static Float validateMetersAboveSeaLevel(String arg){
		float masl;
		try{
			masl = Float.parseFloat(arg);
		} catch (NumberFormatException e){
			return null;
		}
		return masl;
	}

	public static Climate validateClimateNum(String arg){
		int climateNum;
		try {
			climateNum = Integer.parseInt(arg);
		} catch (NumberFormatException e) {
			return null;
		}
		if (climateNum < 1 || climateNum > Climate.values().length)
			return null;
		return Climate.values()[climateNum-1];
	}

	public static Government validateGovernmentNum(String arg){
		int governmentNum;
		try {
			governmentNum = Integer.parseInt(arg);
		} catch (NumberFormatException e) {
			return null;
		}
		if (governmentNum < 1 || governmentNum > Government.values().length)
			return null;
		return Government.values()[governmentNum-1];
	}

	public static StandardOfLiving validateStandardOfLivingNum(String arg){
		int standardNum;
		try {
			standardNum = Integer.parseInt(arg);
		} catch (NumberFormatException e) {
			return null;
		}
		if (standardNum < 1 || standardNum > StandardOfLiving.values().length)
			return null;
		return StandardOfLiving.values()[standardNum-1];
	}

	public static Leaders validateLeaderNum(String arg){
		int leadersNum;
		try {
			leadersNum = Integer.parseInt(arg);
		} catch (NumberFormatException e) {
			return null;
		}
		if (leadersNum < 1 || leadersNum > Leaders.values().length)
			return null;
		return Leaders.values()[leadersNum-1];
	}

}
