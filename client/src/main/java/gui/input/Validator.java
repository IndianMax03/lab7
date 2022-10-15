package gui.input;

import base.*;

public class Validator {

    private Validator() {
    }

    public static String validateName(String arg) {
        if (arg.isEmpty()) {
            return null;
        } else {
            return arg;
        }
    }

    public static Coordinates validateCoordinates(String arg) {
        String[] coordinates = arg.trim().split(";");

        if (coordinates.length != 2) {
            return null;
        }

        String xLine = coordinates[0];
        String yLine = coordinates[1];
        double x, y;


        x = Double.parseDouble(xLine);
        y = Double.parseDouble(yLine);
        if (y <= (Double) City.getLimitation().get("coordinateY")) {
            throw new NumberFormatException();
        }
        return new Coordinates(x, y);
    }

    public static Float validateArea(String arg) {
        float area;
        try {
            area = Float.parseFloat(arg);
            if (area <= (Float) City.getLimitation().get("area")) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return area;
    }

    public static Integer validatePopulation(String arg) {
        int population;
        try {
            population = Integer.parseInt(arg);
            if (population <= (Integer) City.getLimitation().get("population")) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return population;
    }

    public static Float validateMetersAboveSeaLevel(String arg) {
        float masl;
        try {
            masl = Float.parseFloat(arg);
        } catch (NumberFormatException e) {
            return null;
        }
        return masl;
    }

}
