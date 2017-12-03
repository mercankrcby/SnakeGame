package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author macboookair
 */
public class FXMLController implements Initializable {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private GridPane gridPane;

	@FXML
	private TextField tfTime;
	

	@FXML
	private TextField tfEatenJobNum;
	
	@FXML
	private TextField tfGameSpeed;
	
	@FXML
	private TextField tfScore;
	
	@FXML
	private TextField tfLog;
	
	@FXML
	private TextArea taLog;

	private  static final int TABLE_SIZE = 11;
	private static final int ROUND_EAT_BOUND = 5;
	private static final int GAME_START_SPEED = 300; // refresh each 300ms

	private ArrayList<Piece> snakeBody;

	private Snake snake;
	private Piece bait;

	private Random random;

	private int x = 1;
	private int y = 0;

	private final Object lock = new Object();
	// if false run, true wait
	private boolean freezeGame = false;

	private int gameSpeed = GAME_START_SPEED; // move each 300ms
	
	private int eatNumPerRound = 0;
	private int totalEatNum = 0;
	private int maxTime;

	private boolean die = false;

	/**
	 * Initializes the controller class.
	 */
	public void initialize(URL url, ResourceBundle rb) {

		random = new Random();
		snakeBody = new ArrayList<>();

		for (int i = 0; i < snakeBody.size(); ++i) {
			gridPane.add(snakeBody.get(i), snakeBody.get(i).x, snakeBody.get(i).y);
		}

		anchorPane.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.RIGHT) {
					x = 1;
					y = 0;
				} else if (keyEvent.getCode() == KeyCode.DOWN) {
					y = 1;
					x = 0;
				} else if (keyEvent.getCode() == KeyCode.UP) {
					y = -1;
					x = 0;
				} else if (keyEvent.getCode() == KeyCode.LEFT) {
					y = 0;
					x = -1;
				}
			}
		});

		System.out.println("FXML Controller initialized!");
	}

	private Thread getNewTimerTask() {
		return new Thread(new Task<Void>() {
			@Override
			public Void call() {
				while (!die) {
					try {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {

								synchronized (lock) {
									if (!freezeGame) {
										tfTime.setText("" + maxTime);
										if (maxTime == 0) {
											die = true;
										}
										--maxTime;
									}
								}

							}
						});
						Thread.sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				return null;
			}
		});
	}

	private Thread getNewSnakeTask() {
		return new Thread(new Task<Void>() {
			@Override
			public Void call() {
				while (!die) {

					try {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {

								synchronized (lock) {
									if (!freezeGame) {
										updateGrid(x, y);
									}
								}
							}
						});

						Thread.sleep(gameSpeed);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				tfScore.setText(String.valueOf(snake.getPoint()*totalEatNum));
				
				taLog.appendText("\nGAME OVER. YOU HAVE GOT x"+snake.getPoint()+" BONUS.");
				
				
				return null;
			}
		});
	}

	public void btnStart() throws InterruptedException {

		maxTime = 5;
		tfEatenJobNum.setText(String.valueOf(totalEatNum));
		
		gameSpeed = GAME_START_SPEED;
		tfGameSpeed.setText(String.valueOf(gameSpeed));
		
		tfScore.setText("0");
		
		x = 1;
		y = 0;

		snake = new Python();
		taLog.setText(snake.getDescription());

		die = false;

		gridPane.getChildren().removeAll(snakeBody);
		snakeBody.clear();

		gridPane.getChildren().remove(bait);

		snakeBody.add(getPiece(TABLE_SIZE / 2, TABLE_SIZE / 2));
		gridPane.add(snakeBody.get(0), snakeBody.get(0).x, snakeBody.get(0).y);

		bait = getRandomBait();
		gridPane.add(bait, bait.x, bait.y);

		getNewTimerTask().start();
		getNewSnakeTask().start();

	}

	private void updateGrid(int x, int y) {

		Piece prev = new Piece(snakeBody.get(0));
		Piece next = new Piece(snakeBody.get(0));

		snakeBody.get(0).x += x;
		snakeBody.get(0).y += y;

		if (snakeBody.get(0).x < 0 || snakeBody.get(0).y < 0 || snakeBody.get(0).x >= TABLE_SIZE
				|| snakeBody.get(0).y >= TABLE_SIZE) {
			die = true;
		} else {

			// if head crash with bait
			if (bait.x == snakeBody.get(0).x && bait.y == snakeBody.get(0).y) {
				// delete bait, update snake and add new bait
				gridPane.getChildren().remove(bait);
				// add a new node/piece to snake
				snakeBody.add(getPiece(0, 0));

				bait = getRandomBait();
				gridPane.add(bait, bait.x, bait.y);

				maxTime += 3;
				++totalEatNum;
				tfEatenJobNum.setText(String.valueOf(totalEatNum));

				++eatNumPerRound;
				if (eatNumPerRound == ROUND_EAT_BOUND) {
					eatNumPerRound = 0;
					
					// freeze game until user choice a attribute
					synchronized (lock) {
						freezeGame = true;
					}
					
					snake = AttributeAdder.display(snake);
					taLog.setText(snake.getDescription());
					
					gameSpeed-=25;
					tfGameSpeed.setText(String.valueOf(gameSpeed));

					System.out.println(snake.getDescription());
					synchronized (lock) {
						freezeGame = false;
					}
				}
			}

			gridPane.getChildren().remove(snakeBody.get(0));
			gridPane.add(snakeBody.get(0), snakeBody.get(0).x, snakeBody.get(0).y);

			for (int i = 1; i < snakeBody.size(); ++i) { // update each peace with previous
				next.setCoords(snakeBody.get(i));
				snakeBody.get(i).setCoords(prev);
				gridPane.getChildren().remove(snakeBody.get(i));
				gridPane.add(snakeBody.get(i), snakeBody.get(i).x, snakeBody.get(i).y);
				prev.setCoords(next);
			}
		}

	}

	private Piece getPiece(int x, int y) {
		Piece piece = new Piece(x, y);
		piece.setVisible(true);
		piece.setRadius(10);
		return piece;
	}

	private Piece getRandomBait() {
		Piece piece = new Piece(Math.abs(random.nextInt()) % TABLE_SIZE, Math.abs(random.nextInt()) % TABLE_SIZE);
		piece.setVisible(true);
		piece.setRadius(10);
		piece.setFill(Color.RED);
		return piece;
	}

}