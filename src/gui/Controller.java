package gui;

import ScoresDB.Database;
import data.GameBoard;
import data.Snake;
import enums.Direction;
import enums.Level;
import enums.TileState;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.sql.SQLException;

import data.Score;
import javafx.util.Duration;


public class Controller {

	@FXML
	private StackPane stack;

	@FXML
	private ImageView loadscreen;
	@FXML 
	private BorderPane canvas,hscorepane;
	
	@FXML
	private GridPane grid;
	
	@FXML
	private Text scoreText, scoreText2, levelText;
	
	@FXML
	private TableView<Score> scoreTable;
	
	@FXML
	private TableColumn<Score, String> nameColumn;
	@FXML
	private TableColumn<Score, String> scoreColumn;
	@FXML
	private TableColumn<Score, String>dateColumn;

	@FXML
	private TabPane tabpane;
	@FXML
	private TextField nameEntry;
	@FXML
	private Button newGame,quit,back,enter;
	@FXML
	private Pane background;

	private GameBoard board;
	private Snake snake;
	private Database hscores;
	
	private boolean paused;
	private boolean leveled;
	
	private int obstacleCounter;

	private Level level = Level.LEVEL_1;


	public void initialize() {
		board = level.setNewBoard();
		snake = board.getSnake();
		hscores = new Database();
		tabpane.getStyleClass().add("tabs");
		newGame.getStyleClass().add("btn");
		canvas.getStyleClass().add("canvas");
		hscorepane.getStyleClass().add("canvas");
		grid.getStyleClass().add("grid");
		quit.getStyleClass().add("btn");
		back.getStyleClass().add("btn");
		enter.getStyleClass().add("btn");
		canvas.setOnKeyPressed(k -> handlePress(k.getCode()));
		
		checkBoard();
		board.createFood();
		scoreText.setText("" + board.getScore());
		
		populatehighscores();
		
		nameColumn.setCellValueFactory(
				cellData -> cellData.getValue().getName());
		scoreColumn.setCellValueFactory(
				cellData -> cellData.getValue().getScore());
		scoreTable.setItems(hscores.getScores());
		loading();

	}
	private void loading(){
		FadeTransition fadeOut = new FadeTransition(Duration.millis(6000));
		fadeOut.setNode(tabpane);
		fadeOut.setFromValue(0.0);
		fadeOut.setToValue(1.0);
		fadeOut.setCycleCount(1);
		fadeOut.setAutoReverse(false);
		fadeOut.playFromStart();

	}
	public void newGame(){
		board.setScore(0);
		reset();
	}
	private void checkTileState(int i, int j){
		TileState currentTile = board.getTiles().get(i).get(j);
		if (currentTile == TileState.EMPTY) {
			grid.add(new Rectangle(25, 25, Color.WHITE), i, j);
		}
		if (currentTile == TileState.BORDER) {
			grid.add(new Rectangle(25, 25, Color.BLACK), i, j);
		}
		if (currentTile == TileState.SNAKE ||
				currentTile == TileState.SNAKE_HEAD) {
			Rectangle temp = new Rectangle(25, 25, Color.GREEN);
			temp.setStroke(Color.BLACK);
			grid.add(temp, i, j);
		}
		if (currentTile == TileState.POWERED_SNAKE ||
				currentTile == TileState.POWERED_SNAKE_HEAD) {
			Rectangle temp = new Rectangle(25, 25, Color.NAVY);
			temp.setStroke(Color.BLACK);
			grid.add(temp, i, j);
		}
		if (currentTile == TileState.OBSTACLE) {
			grid.add(new Rectangle(25, 25, Color.DARKGRAY), i, j);
		}
		if (currentTile == TileState.POWER_UP) {
			grid.add(new Rectangle(25, 25, Color.NAVY), i, j);
		}

		if (currentTile == TileState.GAME_OVER) {
			grid.add(new Rectangle(25, 25, Color.MAROON), i, j);
			endgame();

		}
		if (currentTile == TileState.FOOD) {
			grid.add(new Rectangle(25, 25, Color.RED), i, j);
		}
	}
	public void endgame(){
		pause();
		Alert gameover = new Alert(Alert.AlertType.INFORMATION, "Game Over");
		gameover.show();
		scoreText2.setText(scoreText.getText());
		gotoHighscores();
	}
	private void checkBoard() {
		grid.requestFocus();
		grid.getChildren().clear();
		for (int i = 0; i < board.getTiles().size(); i++) {
			for (int j = 0; j < (board.getTiles().get(i)).size(); j++) {
				checkTileState(i, j);
			}
		}
	}

	public void handlePress(KeyCode code) {
		grid.requestFocus();
		
		if (code == KeyCode.UP || code == KeyCode.W) {
			goUp();
		}
		if (code == KeyCode.DOWN || code == KeyCode.S) {
			goDown();
		}
		if (code == KeyCode.LEFT || code == KeyCode.A) {
			goLeft();
		}
		if (code == KeyCode.RIGHT || code == KeyCode.D) {
			goRight();
		}

		if (code == KeyCode.P) {
			checkPause();
		}
	}
	
	private void checkPause(){
		if (paused) {
			start();
		}
		else {
			pause();
		}
	}
	
	public void goUp() {
		if (snake.getDirection() != Direction.DOWN) {
			snake.changeDirection(Direction.UP);
		}
	}
	
	public void goDown() {
		if (snake.getDirection() != Direction.UP) {
			snake.changeDirection(Direction.DOWN);
		}
	}
	
	public void goLeft() {
		if (snake.getDirection() != Direction.RIGHT) {
			snake.changeDirection(Direction.LEFT);
		}
	}
	
	public void goRight() {
		if (snake.getDirection() != Direction.LEFT) {
			snake.changeDirection(Direction.RIGHT);
		}
	}
	
	public void pause() {
		paused = true;
		moveTimer.stop();
	}
	
	public void start() {
		paused = false;
		moveTimer.start();
	}
	
	public void reset() {
		board = level.setNewBoard();
		snake = board.getSnake();
		checkBoard();
		board.createFood();
		start();
	}
	
	private long MOVE_PER_SEC = 20L;
	private long MOVE_INTERVAL = 5000000000L / MOVE_PER_SEC;

	private AnimationTimer moveTimer = new AnimationTimer() {
		private long last = 0;
		
		@Override
		public void handle(long now) { 
			if (now - last > MOVE_INTERVAL) {
				last = now;
				board.moveSnake();
				checkBoard();
				scoreText.setText("" + board.getScore());
				board.wakeSnake();

				if (board.getScore() == 5 && !leveled) {
					level = Level.LEVEL_2;
					reset();
					levelText.setText("2");
					leveled = true;
					MOVE_PER_SEC = board.getSnake_speed();
					MOVE_INTERVAL = 5000000000L / MOVE_PER_SEC;
				}
				else if (board.getScore() == 10 && !leveled) {
					level = Level.LEVEL_3;
					reset();
					levelText.setText("3");
					leveled = true;
					MOVE_PER_SEC = board.getSnake_speed();
					MOVE_INTERVAL = 5000000000L / MOVE_PER_SEC;
				}
				else if (board.getScore() != 5 && board.getScore() != 10
						&& board.getScore() != 15) {
					leveled = false;
				}
				else if (board.getScore() == 15 && !leveled) {
					level = Level.LEVEL_4;
					reset();
					levelText.setText("4");
					leveled = true;
					MOVE_PER_SEC = board.getSnake_speed();
					MOVE_INTERVAL = 5000000000L / MOVE_PER_SEC;
				}
				if (level == Level.LEVEL_2 || level == Level.LEVEL_4) {
					obstacleCounter++;
					if (obstacleCounter == 30) {
						obstacleCounter = 0;
						board.createRandomWall();
						board.createPowerUp();
					}
				}

			}
		}
	};

	public void populatehighscores(){
		try {
			hscores.load_db();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public void gotoHighscores(){
		enter.setDisable(false);
		populatehighscores();
		tabpane.getSelectionModel().select(1);

	}
	public void gotoGame(){
		tabpane.getSelectionModel().select(0);
		grid.requestFocus();
	}
	public void insertScore(){
		try {
			enter.setDisable(true);
			hscores.add_score(scoreText2.getText(), nameEntry.getText());
			scoreTable.setItems(hscores.getScores());
			scoreText2.setText("");
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.show();
		}
	}

	
}
