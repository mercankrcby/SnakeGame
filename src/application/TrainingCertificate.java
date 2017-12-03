package application;

public class TrainingCertificate extends SnakeDecorator {
	private Snake snake;
	private final double BONUS=3;
	
	public TrainingCertificate(Snake snake) {
		this.snake=snake;
		this.snake.description= snake.getDescription()+", TrainingCertificate(x"+BONUS+")";
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
