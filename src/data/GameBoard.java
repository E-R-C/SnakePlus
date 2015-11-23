package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import enums.TileState;

public class GameBoard {
	
	private int width;
	private int height; 
	private Snake snake;
	
	private ObservableList<ObservableList<TileState>> board = FXCollections.observableArrayList();
	
	public GameBoard(Snake snake, int height, int width) {
		this.snake = snake;
		this.height = height;
		this.width = width;
		
		for (int i = 0; i < width; i++) {
			board.add(FXCollections.observableArrayList());
			for (int j = 0; j < height; j++) {
				Tuple tuple = new Tuple(i, j);
				if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
					board.get(i).add(TileState.BORDER);
				}
				else if (snake.getBody().contains(tuple)) {
					board.get(i).add(TileState.SNAKE);
				}
				else {
					board.get(i).add(TileState.EMPTY);
				}
				
			}
		}
		
	}
	
	public ObservableList<ObservableList<TileState>> getBoard() {
		return board;
	}
	
	
}
