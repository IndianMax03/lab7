package base;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class City implements Comparable<City>, Serializable {

	private static final long serialVersionUID = -3000033697508215511L;

	private static final Map<String, ? super Number> limitation = new HashMap<>();

	transient private final ResourceBundle RB = ResourceBundle.getBundle("base");

	static {
		limitation.put("id", 0);
		limitation.put("coordinateY", -628d);
		limitation.put("area", 0f);
		limitation.put("population", 0);
	}

	private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
	private String name; //Поле не может быть null, Строка не может быть пустой
	private Coordinates coordinates; //Поле не может быть null
	private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
	private float area; //Значение поля должно быть больше 0
	private int population; //Значение поля должно быть больше 0
	private float metersAboveSeaLevel;
	private Climate climate; //Поле не может быть null
	private Government government; //Поле не может быть null
	private StandardOfLiving standardOfLiving; //Поле может быть null
	private Human governor; //Поле не может быть null
	private String login;

	public City(String name, Coordinates coordinates, float area, int population, float metersAboveSeaLevel, Climate climate, Government government, StandardOfLiving standardOfLiving, Human governor) {
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

	public City(Integer id, String name, double x, double y, Date crDate, float area, int population, float meters, String climate,
	            String government, String standard, String governor, int governor_height, Date governor_birthday, String login){
		this.id = id;
		this.name= name;
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
		return "City{" +
				"id=" + id +
				", name='" + name + '\'' +
				", coordinates=" + coordinates +
				", creationDate=" + creationDate +
				", area=" + area +
				", population=" + population +
				", metersAboveSeaLevel=" + metersAboveSeaLevel +
				", climate=" + climate +
				", government=" + government +
				", standardOfLiving=" + standardOfLiving +
				", governor=" + governor +
				'}';
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

	public String toUser(){
		return RB.getString("city") + ": " + name + ", id: " + id + ", " +  RB.getString("gov") + ": "
				+ government + ", " + RB.getString("user") + ": " + login;
	}

	public static Map<String, ? super Number> getLimitation() {
		return limitation;
	}

}
