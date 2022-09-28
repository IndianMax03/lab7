package base;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class City implements Comparable<City>, Serializable {

    private static final long serialVersionUID = -3000033697508215511L;

    private static final Map<String, ? super Number> limitation = new HashMap<>();

    static {
        limitation.put("id", 0);
        limitation.put("coordinateY", -628d);
        limitation.put("area", 0f);
        limitation.put("population", 0);
    }

    private Integer id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть
    // уникальным, Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private ZonedDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться
    // автоматически
    private float area; // Значение поля должно быть больше 0
    private int population; // Значение поля должно быть больше 0
    private float metersAboveSeaLevel;
    private Climate climate; // Поле не может быть null
    private Government government; // Поле не может быть null
    private StandardOfLiving standardOfLiving; // Поле может быть null
    private Human governor; // Поле не может быть null
    private String login;

    public City(String name, Coordinates coordinates, float area, int population, float metersAboveSeaLevel,
                Climate climate, Government government, StandardOfLiving standardOfLiving, Human governor) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.climate = climate;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }

    public City(Integer id, String name, double x, double y, Date crDate, float area, int population, float meters,
                String climate, String government, String standard, String governor, int governor_height,
                Date governor_birthday, String login) {
        this.id = id;
        this.name = name;
        this.coordinates = new Coordinates(x, y);
        setCreationDate(crDate);
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = meters;
        this.climate = Climate.fromString(climate);
        this.government = Government.fromString(government);
        this.standardOfLiving = StandardOfLiving.fromString(standard);
        this.governor = new Human(Leaders.fromString(governor), governor_height, governor_birthday);
        this.login = login;
    }

    @Override
    public String toString() {
        return "[" + "id=" + id + ", name='" + name + '\'' + ", government=" + government + ", user=" + login + ']';
    }

    public Object[] getArray() {
        Object[] fields = new Object[15];
        fields[0] = getId();
        fields[1] = getName();
        fields[2] = getCoordinates().getX();
        fields[3] = getCoordinates().getY();
        fields[4] = getCreationDate();
        fields[5] = getArea();
        fields[6] = getPopulation();
        fields[7] = getMetersAboveSeaLevel();
        fields[8] = getClimate();
        fields[9] = getGovernment();
        fields[10] = getStandardOfLiving();
        fields[11] = getGovernor();
        fields[12] = getGovernor().getHeight();
        fields[13] = getGovernor().getBirthday();
        fields[14] = getLogin();
        return fields;
    }

    public static Optional<City> getCityByArray(Object[] cityArray) {
        if (cityArray.length != 15) {
            return Optional.empty();
        } else {
            try {
                for(Object o : cityArray) {
                    System.out.println(o.getClass());
                }
                int id = (int) cityArray[0];
                String name = (String) cityArray[1];
                double x = (Double) cityArray[2];
                double y = (Double) cityArray[3];
                java.sql.Date crDate = java.sql.Date.valueOf(((Timestamp) cityArray[4]).toLocalDateTime().toLocalDate());
                float area = (Float) cityArray[5];
                int population = (Integer) cityArray[6];
                float masl = (Float) cityArray[7];
                String climate = cityArray[8].toString();
                String government = cityArray[9].toString();
                String standardOfLiving = cityArray[10].toString();
                String governor = ((Human) cityArray[11]).getName();
                int govHeight = (int) cityArray[12];
                java.sql.Date govBirthday = java.sql.Date.valueOf(((Timestamp) cityArray[13]).toLocalDateTime().toLocalDate());
                String login = (String) cityArray[14];
                return Optional.of(new City(id, name, x, y, crDate, area, population, masl, climate, government, standardOfLiving, governor, govHeight, govBirthday, login));
            } catch (NullPointerException | NumberFormatException | NoSuchElementException ex) {
                return Optional.empty();
            }
        }
    }

    public void update(City city) {
        this.coordinates = city.getCoordinates();
        this.creationDate = city.creationDate;
        this.area = city.getArea();
        this.population = city.getPopulation();
        this.metersAboveSeaLevel = city.getMetersAboveSeaLevel();
        this.climate = city.getClimate();
        this.government = city.getGovernment();
        this.standardOfLiving = city.getStandardOfLiving();
        this.governor = city.getGovernor();
    }

    @Override
    public int compareTo(City anotherCity) {
        return this.name.compareTo(anotherCity.getName());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Timestamp getCreationDate() {
        return Timestamp.valueOf(creationDate.toLocalDateTime());
    }

    public void setCreationDate(java.sql.Date creationDate) {
        this.creationDate = creationDate.toLocalDate().atStartOfDay(ZoneId.systemDefault());
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public float getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public void setMetersAboveSeaLevel(float metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public Climate getClimate() {
        return climate;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public void setStandardOfLiving(StandardOfLiving standardOfLiving) {
        this.standardOfLiving = standardOfLiving;
    }

    public Human getGovernor() {
        return governor;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
    }

    public static Map<String, ? super Number> getLimitation() {
        return limitation;
    }

}
