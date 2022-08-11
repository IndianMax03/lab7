package base;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Human implements Serializable {

	private static final long serialVersionUID = 3602164311190816013L;

	private String name; //Поле не может быть null, Строка не может быть пустой
	private Integer height; //Значение поля должно быть больше 0
	private ZonedDateTime birthday;

	@Override
	public String toString() {
		return "Правитель: " + this.name;
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

	public ZonedDateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(ZonedDateTime birthday) {
		this.birthday = birthday;
	}
}
