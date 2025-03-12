import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class p2 {
	
	static boolean Stack = true;
	static boolean Queue = false;
	static boolean Opt = false;
	static boolean Time = false;
	static boolean Incoordinate = false;
	static boolean Outcoordinate = false;
	static boolean Help = false;
	static Map currMap;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readMap("TEST01");
		firstChecks(Stack, Queue, Opt, Time, Incoordinate, Outcoordinate, Help);


		
	}

	public static void firstChecks(boolean s, boolean q, boolean o, boolean t, boolean i, boolean ot, boolean h) {
		if(s && !q && !o) {
			stackSolver();
			
		}
		if(q && !s && !o) {
			queueSolver();
		}
		if(o && !s && !q) {
			shortestPath();
		}
		if(t) {
			runTime();
		}
		if(i) {
			Incoordinate = true;
		}
		if(ot) {
			Outcoordinate = true;
		}
		if(h) {
			System.out.println("Help");
			System.exit(0);
		}
		else{
			System.out.println("Invalid input");
			System.exit(-1);
		}
	}
	
	public static void readMap(String filename) {
		try {
			File file = new File(filename);
			Scanner s = new Scanner(file);
			
			int numRows = s.nextInt();
			int numCols = s.nextInt();
			int numsRooms = s.nextInt();
			currMap = new Map(numRows, numCols);
			
			int rowIndex = 0;
			
			while(s.hasNextLine()) {
				String row = s.nextLine();
				
				if(row.length() > 0) {
					for(int i = 0; i < numCols && i < row.length(); i++ ) {
						char el = row.charAt(i);
						Tile obj = new Tile(rowIndex, i , el);
						currMap.setTile(rowIndex, i, obj);
						
						
						
					}
				}
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

	public static void stackSolver() {
		// TODO Auto-generated method stub

		if(currMap == null) {
			System.out.println("No map found");
			System.exit(-1);
		}

		Tile start = null;
		Tile goal = null;
		Tile prev = null;

		for(int i = 0; i < currMap.getRows(); i++) {
			for(int j = 0; j < currMap.getCols(); j++) {
			Tile tile = currMap.getTile(i, j);
			if(tile.getType() == 'W') {
				start = tile;
			}
			if(tile.getType() == '$') {
				goal = tile;
			}	
		}

		Stack<Tile> stack = new Stack<Tile>();
		Stack<Tile> path = new Stack<Tile>();
		stack.push(start);
		path.push(start);
		start.addPath('+');
		start.setVisited(true);

		while(!stack.isEmpty()){
			Tile current = stack.pop();
			if(current.getType() == '$') {
				Tile currPath = current;
				while(currPath != null) {
					if(currPath.getType() != 'W' && currPath.getType() != '$') {
						currPath.addPath('+');
					}
					
				}
				for(int a = 0; i < currMap.getRows(); a++) {
					for(int d = 0; d < currMap.getCols(); d++) {
						Tile tile = currMap.getTile(a, d);
						if(tile.getType() == '+') {
							currPath = tile;

						


					}
				}



			}
			currMap.returnMaze();





			
		}




		
	}
}


		
	}

	public static void queueSolver() {
		// TODO Auto-generated method stub
		
	}

	public static void shortestPath() {
		// TODO Auto-generated method stub
		
	}

	public static void runTime() {
		// TODO Auto-generated method stub
		
	}


}

