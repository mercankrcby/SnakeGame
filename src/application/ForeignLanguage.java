package application;

public class ForeignLanguage extends SnakeDecorator {

	private Snake snake;
	private final double BONUS = 2.0;

	public ForeignLanguage(Snake snake) {
		this.snake=snake;
		this.snake.description= snake.getDescription()+", ForeignLanguage(x"+BONUS+")";
		this.snake.point = snake.getPoint()*BONUS;
	}

	public String getDescription() {
		return this.snake.description;
	}

	@Override
	public double getPoint() {
		return this.snake.point;
	}
}
