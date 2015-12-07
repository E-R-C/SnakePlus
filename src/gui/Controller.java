package gui;

import ScoresDB.Database;
import data.GameBoard;
import data.Snake;
import enums.Direction;
import enums.TileState;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.awt.*;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;


public class Controller {
	@FXML
	private Button HScoresButton;
	
	@FXML 
	private BorderPane canvas;
	
	@FXML
	private GridPane grid;
	
	@FXML
	private Text scoreText, scoreText2;

	@FXML
	private TabPane tabpane;
	@FXML
	private TextField nameEntry;
	private GameBoard board;
	private Snake snake;
	private boolean gameOver;
	Database hscores;
	
	private boolean paused;
	
	public void initialize() {
		snake = new Snake();
		board = new GameBoard(snake, 10, 10);
		tabpane.getStyleClass().add("tabs");
		
		canvas.setOnKeyPressed(k -> handlePress(k.getCode()));
		
		checkBoard();
		board.createFood();
		scoreText.setText("" + board.getScore());
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
		if (currentTile == TileState.GAME_OVER) {
			grid.add(new Rectangle(25, 25, Color.BROWN), i, j);
			pause();
			Alert gameover = new Alert(Alert.AlertType.CONFIRMATION, "Game Over");
			gameover.show();
			scoreText2.setText(scoreText.getText());
			gotoHighscores();
		}
		if (currentTile == TileState.FOOD) {
			grid.add(new Rectangle(25, 25, Color.RED), i, j);
		}
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
		
//		if (code == KeyCode.ENTER){
//			board.moveSnake();
//			checkBoard();
//		}
		
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
			}
		}
	};

	public void populatehighscores(){
		hscores = new Database();
		try {
			hscores.load_db();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public void gotoHighscores(){
		populatehighscores();
		tabpane.getSelectionModel().select(1);

	}
	public void gotoGame(){
		tabpane.getSelectionModel().select(0);
		grid.requestFocus();
	}
	public void insertScore(){
		try {
			hscores.add_score(scoreText2.getText(), nameEntry.getName());
		} catch (SQLException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.show();
		}
	}

	
}
