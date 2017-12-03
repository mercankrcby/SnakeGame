package application;

public class Internship extends SnakeDecorator {
	private Snake snake;
	private final double BONUS=1.5;
	
	public Internship(Snake snake) {
		this.snake=snake;
		this.snake.description= snake.getDescription()+", Internship(x"+BONUS+")";
		this.snake.point = snake.getPoint()*BONUS;
	}
	
	public String getDescription() {
		return snake.description;
	}
	
	@Override
	public double getPoint() {
		return snake.point;
	}
}
