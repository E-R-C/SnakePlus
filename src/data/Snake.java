package data;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javafx.collections.FXCollections;
import enums.Direction;
import enums.TileState;
import javafx.collections.ObservableList;

public class Snake {
	private int size = 2;
	private Tuple head = new Tuple(3, 16);
	private ArrayDeque<Tuple> body = new ArrayDeque<Tuple>();
	private Direction direction = Direction.UP; 
	
	public Snake() {
		body.add(head); 
		for (int i = size - 1; i >= 0; i--) {
			body.add(new Tuple(head.getX(), head.getY() + i));
		}
	}
	
	public void changeDirection(Direction dir) {
		direction = dir;
	}
	
	public void move() {
		body.remove();
		
		if (direction == Direction.UP) {
			head = new Tuple(head.getX(), head.getY() - 1);
		}
		if (direction == Direction.DOWN) {
			head = new Tuple(head.getX(), head.getY() + 1);
		}
		if (direction == Direction.LEFT) {
			head = new Tuple(head.getX() - 1, head.getY());
		}
		if (direction == Direction.RIGHT) {
			head = new Tuple(head.getX() + 1, head.getY() - 1);
		}
		body.add(head);
	}
	
	public Tuple getHead() {
		return head;
	}
	
	public ArrayDeque<Tuple> getBody() {
		return body;
	}
	
}
