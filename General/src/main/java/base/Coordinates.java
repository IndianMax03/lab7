package base;

import java.io.Serializable;
import java.util.ResourceBundle;

public class Coordinates implements Serializable {

	private static final long serialVersionUID = -6206775671942678111L;
	transient private final ResourceBundle RB = ResourceBundle.getBundle("base");
	private Double x; //Поле не может быть null
	private Double y; //Значение поля должно быть больше -628, Поле не может быть null

	public Coordinates(Double x, Double y){
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return RB.getString("coords") +
				"x=" + x +
				", y=" + y +
				'}';
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}
}
