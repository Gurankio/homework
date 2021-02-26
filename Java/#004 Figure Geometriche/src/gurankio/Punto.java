package gurankio;

import java.io.Serializable;

public class Punto implements Serializable {
	
	private double x;
	private double y;

	public Punto(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Punto() {
		this(0, 0);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double distanza(Punto p) {
		return Math.sqrt(Math.pow(getX() - p.getX(), 2) + Math.pow(getY() - p.getY(), 2));
	}

	@Override
	public String toString() {
		return "Punto{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
