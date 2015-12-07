package data;

import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import enums.TileState;

public class GameBoard {
	
	private int width;
	private int height; 
	private Snake snake;
	private Tuple foodTile = new Tuple(0, 0);
	private String score = "0";
	
	private ObservableList<ObservableList<TileState>> tiles = FXCollections.observableArrayList();
	
	public GameBoard(Snake snake, int height, int width) {
		this.snake = snake;
		this.height = height;
		this.width = width;
		
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
	
	public String getScore() {
		return score;
	}
	
	//Couldn't we just make score at the beginning an int. It would make increasing
	// it easier.  Then when you get score, you just have the code:
	// return " " + score
	// or something of that effect.
	private void incScore() {
		int score = Integer.parseInt(this.score);
		score++;
		this.score = Integer.toString(score);
	}
	
}
