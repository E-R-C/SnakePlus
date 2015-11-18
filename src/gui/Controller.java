package gui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Controller {
	
	@FXML
	private GridPane grid;
	
	private ArrayList<Rectangle> borderRects = new ArrayList<Rectangle>();
	
	public void initialize() {
		
		for (int i = 0; i < 20; i++) {
			grid.add(new Rectangle(25, 25, Color.BLACK), i, 0);
			grid.add(new Rectangle(25, 25, Color.BLACK), i, 19);
		}
		for (int i = 1; i < 19; i++) {
			grid.add(new Rectangle(25, 25, Color.BLACK), 0, i);
			grid.add(new Rectangle(25, 25, Color.BLACK), 19, i);
		}
		
	}
	
}
