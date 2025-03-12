
public class Map {
	private Tile[][] map;

	private int rows;
	private int cols;

	public Map(int rows, int cols) {
		//TODO Auto-generated constructor stub
		this.rows = rows;
		
		this.cols = cols;
		map = new Tile[rows][cols];
		
		

	}

	
	

	public int getRows() {
		return rows;
	}
	public int getCols() {
		return cols;
	}

	public Tile getTile(int row, int col) {
		// TODO Auto-generated method stub
		//implement this method
		if(row >= 0 && col >= 0) {
			return map[row][col];
		}
		return null;
	}

	public void setTile(int row, int col, Tile t) {
		// TODO Auto-generated method stub
		//implement this method
		if(row >= 0 && col >= 0) {
			 map[row][col] = t ;
		}
		
	}

	public String returnMaze() {

		// TODO Auto-generated method stub
		//implement this method
		String maze = "";
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				maze += map[i][j].getType();
			}
			maze += "\n";
		}
		return maze;
	}

	
	
	

}
