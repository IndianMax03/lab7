package base;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class City implements Comparable<City>, Serializable {

	private static final long serialVersionUID = -3000033697508215511L;

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

	public ZonedDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(ZonedDateTime creationDate) {
		this.creationDate = creationDate;
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
}
