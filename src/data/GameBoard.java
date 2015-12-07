package data;

import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import enums.TileState;

public class GameBoard {
	
	private int width, height, snake_speed, score;
	private Snake snake;
	private Tuple foodTile = new Tuple(0, 0);
	
	private ObservableList<ObservableList<TileState>> tiles = FXCollections.observableArrayList();
	
	public GameBoard(Snake snake, int height, int width) {
		this.snake = snake;
		this.height = height;
		this.width = width;
		this.score = 0;
		
		checkTiles();
	}
	
	private void checkTuple(int i, int j){
		Tuple tuple = new Tuple(i, j);
		if (snake.getHead().equals(tuple)) {
			if (i == 0 || i == width - 1 || j == 0 || j == height - 1
					|| snake.getBody().contains(tuple)) {
				tiles.get(i).add(TileState.GAME_OVER);
			}else {
				tiles.get(i).add(TileState.SNAKE_HEAD);
			}					
			if (tuple.equals(foodTile)) {
				snake.eat();
				incScore();
			}
		}else {
			if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
				tiles.get(i).add(TileState.BORDER);
			}else if (snake.getBody().contains(tuple)) {
				tiles.get(i).add(TileState.SNAKE);
			}else if (tuple.equals(foodTile)) {
				tiles.get(i).add(TileState.FOOD);
			}else {
				tiles.get(i).add(TileState.EMPTY);
			}
		}
	}
	
	private void checkTiles() {
		tiles.clear();
		for (int i = 0; i < width; i++) {
			tiles.add(FXCollections.observableArrayList());
			for (int j = 0; j < height; j++) {
				checkTuple(i, j);
			}
		}	
		if (snake.hasEaten()) {
			createFood();
		}
	}
	
	public void createFood() {
		int x = randInt(1, height - 1);
		int y = randInt(1, width - 1);
	
		while (tiles.get(x).get(y) != TileState.EMPTY) {
			x = randInt(1, height - 1);
			y = randInt(1, width - 1);
		}
		foodTile = new Tuple(x, y);
	}
	
	private int randInt(int min, int max) {
	    Random rand = new Random();
	    int num = rand.nextInt((max - min) + 1) + min;
	    return num;
	}
	
	public ObservableList<ObservableList<TileState>> getTiles() {
		return tiles;
	}
	
	public void moveSnake() {
		snake.move();
		checkTiles();
	}
	
	public void wakeSnake() {
		snake.wakeUp();
	}
	
	public void setSnakeSpeed(int spd){
		snake_speed = spd;
	}
	public void incSnakeSpeed(){
		snake_speed++;
	}
	public void decSnakeSpeed(){
		snake_speed--;
	}
	public int getSnake_speed(){
		return snake_speed;
	}
	public int getScore() {
		return score;
	}

	private void incScore() {
		score++;
	}
	
}
