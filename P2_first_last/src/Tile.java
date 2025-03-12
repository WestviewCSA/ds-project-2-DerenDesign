
public class Tile {

	private int row, col;
	private char type;
	private boolean visited;
	private String output;
	
	public Tile(int row, int col, char type) {
		super();
		this.row = row;
		this.col = col;
		this.type = type;
		this.visited = false;
		this.output = output;
	}
	
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public char getType() {
		return type;
	}

	public void addPath(char type){
		this.type = type;
	}
	
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public String toString() {
		return output;
	}
	
	
	
}
