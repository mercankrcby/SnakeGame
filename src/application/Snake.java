package application;

public abstract class Snake {

	String description = "UNKNOWN";
	protected double point;

	public abstract double getPoint();

	public String getDescription() {
		return description;
	}
}
