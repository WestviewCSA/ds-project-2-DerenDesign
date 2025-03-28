import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public class p2 {
	
	static boolean Stack = true;
	static boolean Queue = false;
	static boolean Opt = false;
	static boolean Time = false;
	static boolean Incoordinate = false;
	static boolean Outcoordinate = true;
	static boolean Help = false;
	static Map currMap;
	static Map currMap2;
	static double startTime;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("hi");
		if(Incoordinate && !Help) {
			readtextMap("TEST01");
			System.out.println(currMap.returnMaze());
		}
		if(!Incoordinate) {
			readCoordinateMap("TEST07");
			
			System.out.println(currMap2);
			System.out.println(currMap2.returnMaze());
			}
		
		//firstChecks(Stack, Queue, Opt, Time, Incoordinate, Outcoordinate, Help);


		
	}

	

	public static void firstChecks(boolean s, boolean q, boolean o, boolean t, boolean i, boolean ot, boolean h) {
		if(s && !q && !o && !h) {
			stackSolver();
			
			
		}
		if(q && !s && !o && !h) {
			queueSolver();
		}
		if(o && !s && !q && !h) {
			shortestPath();
		}
		if(t && !h) {
			runTime();
		}
		if(i && !h) {
			Incoordinate = true;
		}
		if(ot && !h) {
			Outcoordinate = true;
		}
		if(h) {
			System.out.println("This program's goal is to help Wolverine find the Diamond Wolverine Coin!");
			System.out.println("If Stack, Queue, or Opt, are set to true, I will find the stack, queue, or shortest path.");
			System.out.println("If the Time switch is set I will return the runtime.");
			System.out.println("Depending on the Text file input, either text-map or coordinate-map, I will output the same style.");
			System.exit(0);
		}
		
			
			System.exit(-1);
		
	}
	
	public static void readtextMap(String filename) {
		try {
			File file = new File(filename);
			Scanner s = new Scanner(file);
			
			int numRows = s.nextInt();
			int numCols = s.nextInt();
			int numsRooms = s.nextInt();
			currMap = new Map(numRows, numCols, numsRooms);
			s.nextLine();
			
			int r = 0;
			while(s.hasNextLine() && r < numRows) {
				String row = s.nextLine();
				
				if(row.length() > 0) {
					for(int i = 0; i < numCols && i < row.length(); i++ ) {
						char el = row.charAt(i);
						currMap.setTile(r, i, new Tile(r, i, el));
					}
					r++;
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
	
	
	private static void readCoordinateMap(String string) {
		try {
			File file = new File(string);
			Scanner s = new Scanner(file);

			s.nextLine(); //skip header

			int xnumCols = 7; // Sets cols to 4 for some reason?
			int xnumsRooms = 1;

			//Determine the number of rows
			LinkedList<String> rows = new LinkedList<>();
			while (s.hasNextLine()) {
				rows.add(s.nextLine());
			}
			int xnumRows = rows.size();

			currMap2 = new Map(xnumRows, xnumCols, xnumsRooms); // Create the map with row count

			int a = 0; // Start from the first row
			for (String row : rows) {
				if (row.length() > 0) {
					
					

					for (int i = 0; i < xnumCols; i++) {
						char el = row.charAt(i);
						Tile obj = new Tile(a, i, el);
						currMap2.setTile(a, i, obj);
					}
					a++;
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}

	public static void stackSolver() {
	  
	    if (Time) {
	        startTime = System.currentTimeMillis();
	    }

	    Map activeMap;

	    if (Incoordinate) {
	        activeMap = currMap;
	    } else if (Outcoordinate) {
	        activeMap = currMap2;
	    } else {
	        activeMap = null;
	    }

	    if (activeMap == null) {
	        System.out.println("No map found");
	        System.exit(-1);
	    }

	    int rows = activeMap.getRows();
	    int cols = activeMap.getCols();
	    Tile start = null;
	    Tile goal = null;

	    
	    for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {
	            Tile t = activeMap.getTile(i, j, 0);
	            if (t != null) {
	                if (t.getType() == 'W') {
	                    start = t;
	                } else if (t.getType() == '$') {
	                    goal = t;
	                }
	            }
	        }
	    }

	    if (start == null || goal == null) {
	        System.out.println("Start or goal not found.");
	        return;
	    }

	    Stack<Tile> stack = new Stack<>();
	    Stack<Tile> solutionPath = new Stack<>();
	    stack.push(start);
	    start.setVisited(true);

	    // Movement directions (North, East, South, West)
	    int[][] directions = {
	        {-1, 0}, // North
	        {0, 1},  // East
	        {1, 0},  // South
	        {0, -1}  // West
	    };

	    boolean found = false;

	    while (!stack.isEmpty() && !found) {
	        Tile current = stack.peek();

	        // If we found the goal, stop searching
	        if (current == goal) {
	            found = true;
	            solutionPath.push(current);
	            break;
	        }

	        boolean moved = false;

	        // Explore NESW
	        for (int[] dir : directions) {
	            int newRow = current.getRow() + dir[0];
	            int newCol = current.getCol() + dir[1];
	            Tile neighbor = activeMap.getTile(newRow, newCol, 0);

	            if (neighbor != null && (neighbor.getType() == '.' || neighbor.getType() == '$') && !neighbor.isVisited()) {
	                stack.push(neighbor);
	                neighbor.setVisited(true);
	                solutionPath.push(current);
	                moved = true;
	                break;
	            }
	        }

	        if (!moved) {
	            stack.pop();
	            if (!solutionPath.isEmpty()) {
	                solutionPath.pop();
	            }
	        }
	    }

	    // Print the solution path
	    if (found) {
	        
	        while (!solutionPath.isEmpty()) {
	            Tile pathTile = solutionPath.pop();
	            System.out.println("+ " + pathTile.getRow() + " " + pathTile.getCol() + " 0");
	            if (pathTile.getType() != 'W' && pathTile.getType() != '$') {
	                pathTile.addPath('+');
	            }
	        }
	    } 
	        

	    // Print the solved map
	    System.out.println(activeMap.returnMaze());

	    runTime();
	}
	
	





		
	

	public static void queueSolver() {
		// TODO Auto-generated method stub

		if(Time) {
			startTime = System.currentTimeMillis();
		}
		Map activeMap;
		if (Incoordinate) {
   			 activeMap = currMap;
		} else if (Outcoordinate) {
    		activeMap = currMap2;
		} else {
    		activeMap = null;
		}
		if (activeMap == null) {

		if (activeMap == null) {
			System.out.println("No map found");
			System.exit(-1);
		}
	
		int rows = activeMap.getRows();
		int cols = activeMap.getCols();
		Tile start = null;
		Tile goal = null;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Tile t = activeMap.getTile(i, j, 0);
				if (t != null) {
					if (t.getType() == 'W') {
						start = t;
					} else if (t.getType() == '$') {
						goal = t;
					}
				}
			}
		}
	 
		Queue<Tile> queue = new LinkedList<>(); // empty stack
		Queue<Tile> solutionPath = new LinkedList<>(); // store answer
		//push start to stack
		queue.add(start);
		start.setVisited(true);
	
		// Movement directions (North, East, South, West)
		int[][] directions = {
			{-1, 0}, // North
			{0, 1},  // East
			{1, 0},  // South
			{0, -1}  // West
		};
		//keep track of if we found the goal
		boolean found = false;
	
		while (!queue.isEmpty() && !found) {
			Tile current = queue.poll(); // Peek the top of the stack (no pop)
	
			// if we found the $ stop looking
			if (current == goal) {
				found = true;
				solutionPath.add(current);
				break;
			}
			
			boolean moved = false;
	
			// Explore NESW
			for (int i = 0; i < directions.length; i++) {
				int[] dir = directions[i];
				//new row and col to check if we can move there or not
				int newRow = current.getRow() + dir[0];
				int newCol = current.getCol() + dir[1];
				//check if we can move there or not
				Tile neighbor = activeMap.getTile(newRow, newCol, 0);
				if (neighbor != null && (neighbor.getType() == '.' || neighbor == goal) && !neighbor.isVisited()) {
					queue.add(neighbor); 
					neighbor.setVisited(true);
					moved = true;
					solutionPath.add(current); 
					break; 
				}
			}
			//if we can't move pop the stack
			if (!moved) {
				queue.poll(); 
				if (!solutionPath.isEmpty()) {
					solutionPath.poll(); 
				}
			}
		}
	
		
	
		// Print the maze with the path
		while (!solutionPath.isEmpty()) {
			Tile pathTile = solutionPath.poll();
			if (pathTile != start && pathTile != goal) {
				pathTile.addPath('+');
			}
		}

		
		
		

		
		System.out.println(currMap.returnMaze());
		runTime();
	}
}

	//start queue implementation
	
		
		
	
	

	public static void shortestPath() {
		// TODO Auto-generated method stub
		stackSolver();
	}

	public static void runTime() {
		// TODO Auto-generated method stub

		

		

		double endTime = System.currentTimeMillis();
		double totalTime = (endTime - startTime) / 1000.0;
		
		System.out.println("Total Runtime: " + totalTime + " seconds");
		
	}


}

