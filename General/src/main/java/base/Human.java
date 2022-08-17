package base;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Human implements Serializable {

	private static final long serialVersionUID = 3602164311190816013L;

	private String name; //Поле не может быть null, Строка не может быть пустой
	private Integer height; //Значение поля должно быть больше 0
	private ZonedDateTime birthday;

	private Human(String name, Integer height){
		this.name = name;
		this.height = height;
		this.birthday = ZonedDateTime.now();
	}

	public Human(Leaders leader, Integer height, java.sql.Date birthday){
		this.name = leader.toString();
		this.height = height;
		setBirthday(birthday);
	}

	public static Human newRandomHuman(){
		int nameNum = (int) (Math.random() * 5);
		int height =(int) (Math.random() * 251);
		return new Human(Leaders.getFromNum(nameNum), height);
	}

	public static Human newHumanByLeader(Leaders leaders){
		int height =(int) (Math.random() * 251);
		return new Human(leaders.toString(), height);
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Timestamp getBirthday() {
		return Timestamp.valueOf(birthday.toLocalDateTime());
	}

	public void setBirthday(java.sql.Date creationDate) {
		this.birthday = creationDate.toLocalDate().atStartOfDay(ZoneId.systemDefault());
	}
}
