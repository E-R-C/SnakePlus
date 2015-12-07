package enums;

import data.GameBoard;
import data.Snake;

public enum Level {
	LEVEL_1 (15, 15, 20L, 0),
	LEVEL_2 (15, 15, 22L, 5),
	LEVEL_3 (12, 12, 25L, 10),
	LEVEL_4 (20, 20, 30L, 15);
	
	private final int height;
    private final int width;
    private final long speed;
    private final int score;
    private GameBoard board;
    private Snake snake = new Snake();
    
    Level(int height, int width, long speed, int score) {
        this.height = height;
        this.width = width;
        this.speed = speed;
        this.score = score;
    }
    
    public GameBoard setNewBoard(){
        snake = new Snake();
    	board = new GameBoard(snake, height, width);
//    	board.setSnakeSpeed(speed);
    	board.setScore(score);
    	return board;
    }
}