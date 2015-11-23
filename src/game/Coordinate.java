package game;




public class Coordinate {
	private int column;
	private int row;
	
	private Coordinate(int x, int y){
		column = x;
		row = y;
	}
	
	public int getColumn(){return column;}
	public int getRow(){return row;}

}
