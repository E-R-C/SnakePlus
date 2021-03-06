package data;

import java.util.ArrayDeque;
import enums.Direction;

public class Snake {
	private int size = 2;
	private Tuple head = new Tuple(3, 3);
	private ArrayDeque<Tuple> body = new ArrayDeque<Tuple>();
	private Direction direction = Direction.DOWN;
	private boolean resting;
	private boolean eaten;
	
	public Snake() {
		for (int i = size - 1; i > 0; i--) {
			body.add(new Tuple(head.getX(), head.getY() - i));
		}
	}

	public Direction getDirection() {
		return direction;
	}
	
	public void changeDirection(Direction dir) {
		if (!resting) {
			direction = dir;
			resting = true;
		}
	}
	
	public void move() {
		if (!eaten) {
			body.remove();
		}else {
			eaten = false;
		}
		
		body.add(head);
		
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
			head = new Tuple(head.getX() + 1, head.getY());
		}
		
	}
	
	public Tuple getHead() {
		return head;
	}
	
	public ArrayDeque<Tuple> getBody() {
		return body;
	}
	
	public void wakeUp() {
		resting = false;
	}
	
	public void eat() {
		eaten = true;
	}
	
	public boolean hasEaten() {
		return eaten;
	}
	
}
