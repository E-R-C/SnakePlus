package gui;

import java.util.ArrayList;

import data.GameBoard;
import data.Snake;
import enums.TileState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Controller {
	
	@FXML
	private GridPane grid;
	
	private ObservableList<TileState> tiles = FXCollections.observableArrayList();
	private GameBoard board;
	private Snake snake;
	
	public void initialize() {
		snake = new Snake();
		board = new GameBoard(snake, 20, 20);
		
		for (int i = 0; i < board.getBoard().size(); i++) {
			for (int j = 0; j < (board.getBoard().get(i)).size(); j++) {
				if (board.getBoard().get(i).get(j) == TileState.EMPTY) {
					grid.add(new Rectangle(25, 25, Color.WHITE), i, j);
				}
				if (board.getBoard().get(i).get(j) == TileState.BORDER) {
					grid.add(new Rectangle(25, 25, Color.BLACK), i, j);
				}
				if (board.getBoard().get(i).get(j) == TileState.SNAKE) {
					grid.add(new Rectangle(25, 25, Color.GREEN), i, j);
				}
			}
		}
	}
	
	
	
}
