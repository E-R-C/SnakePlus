package data;

public class Tuple {
	int x;
	int y;
	
	public Tuple(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Tuple)) {
			return false;
		}else {
			return x == ((Tuple) other).x && y == ((Tuple) other).y;
		}
	}
}
