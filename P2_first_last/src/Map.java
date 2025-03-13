
public class Map {
	private Tile[][][] map;

	private int rows;
	private int cols;
	private int rooms;

	public Map(int rows, int cols, int rooms) {
		//TODO Auto-generated constructor stub
		this.rows = rows;
		
		this.cols = cols;
		map = new Tile[rows][cols][rooms];
		this.rooms = rooms;
		
		

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
			return map[row][col][rooms];
		}
		return null;
	}

	public void setTile(int row, int col, Tile t) {
		// TODO Auto-generated method stub
		//implement this method
		
		
		if(row >= 0 && col >= 0) {
			 map[row][col][rooms-1] = t ;
		}
		
	}

	public String returnMaze() {

		// TODO Auto-generated method stub
		//implement this method
		String maze = "";
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				for(int k = 0; k < map[i][j].length; k++) {
					maze +=  map[i][j][k].getType();
					
				}
				
			}
			maze += "\n";
		}
		return maze;
	}

	
	
	

}
