package data;

import java.util.ArrayList;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import enums.TileState;

public class GameBoard {
	
	private int width, height, score;
	private long snakeSpeed;
	private Snake snake;
	private Tuple foodTile = new Tuple(0, 0);
	private ArrayList<Tuple> tempWallList = new ArrayList();
	private boolean powered;
	
	private ObservableList<ObservableList<TileState>> tiles = FXCollections.observableArrayList();

	public GameBoard(Snake snake, int height, int width) {
		this.snake = snake;
		this.height = height;
		this.width = width;
		this.score = 0;
		
		snakeSpeed = 20L;
		
		checkTiles();
	}
	
	private void checkTuple(int i, int j){
		Tuple tuple = new Tuple(i, j);
		if (snake.getHead().equals(tuple)) {
			if (i == 0 || i == width - 1 || j == 0 || j == height - 1
					|| snake.getBody().contains(tuple)) {
				tiles.get(i).add(TileState.GAME_OVER);
			}
			
			else if (tempWallList.contains(tuple)) {
				if (powered) {
					tiles.get(i).add(TileState.SNAKE_HEAD);
				}
				else {
					tiles.get(i).add(TileState.GAME_OVER);
				}
				
			}
			
			else {
				if (powered) {
					tiles.get(i).add(TileState.POWERED_SNAKE_HEAD);
				}
				else {
					tiles.get(i).add(TileState.SNAKE_HEAD);
				}
			}					
			if (tuple.equals(foodTile)) {
				snake.eat();
				incScore();
			}
		}else {
			if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
				tiles.get(i).add(TileState.BORDER);
			}else if (snake.getBody().contains(tuple)) {
				if (powered) {
					tiles.get(i).add(TileState.POWERED_SNAKE);
				}
				else {
					tiles.get(i).add(TileState.SNAKE);
				}
			}else if (tempWallList.contains(tuple)){
				tiles.get(i).add(TileState.OBSTACLE);
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

	public boolean isValidSquare(int x, int y){
		return tiles.get(x).get(y) == TileState.EMPTY;
	}

	private Tuple create(){
		int x = randInt(1, height - 1);
		int y = randInt(1, width - 1);

		while (!isValidSquare(x,y)) {
			x = randInt(1, height - 1);
			y = randInt(1, width - 1);
		}
		return new Tuple(x,y);
	}
	public void createrRandomWall(){
		tempWallList.add(create());
	}
	public void createFood() {
		foodTile = create();
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

	public void setScore(int scr){
		score = scr;
	}
	public void setSnakeSpeed(long spd){
		snakeSpeed = spd;
	}
	public void incSnakeSpeed(){
		
	}
	public void decSnakeSpeed(){
		
	}
	public long getSnake_speed(){
		return snakeSpeed;
	}
	public int getScore() {
		return score;
	}

	private void incScore() {
		score++;
	}
	
}
