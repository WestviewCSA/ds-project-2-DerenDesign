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
			
			
			
			}
		
		firstChecks(Stack, Queue, Opt, Time, Incoordinate, Outcoordinate, Help);


		
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

        if (!s.hasNextInt()) {
            System.out.println("Invalid file format: Missing dimensions.");
            s.close();
            return;
        }

        int numRows = s.nextInt(); // Read the number of rows
        int numCols = s.nextInt(); // Read the number of columns
        int numRooms = s.nextInt(); // Read the number of rooms

        currMap2 = new Map(numRows, numCols, numRooms); // Create the map

        // Move to the next line 
        if (s.hasNextLine()) {
            s.nextLine();
        }

		for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                currMap2.setTile(i, j, new Tile(i, j, '.'));
            }
        }

        
        while (s.hasNextLine()) {
            String row = s.nextLine().trim();
            if (!row.isEmpty()) {

                String[] parts = row.split("\\s+");

              
                if (parts.length >= 4) {
                    
                    String element = parts[0];
                    int r = Integer.parseInt(parts[1]); // Row index
                    int c = Integer.parseInt(parts[2]); // Column index
                    int level = Integer.parseInt(parts[3]); // Level index
                    System.out.println(element + " " + r + " " + c + " " + level);

                    // Create and set the tile (no changes to the row data)
                    Tile newTile = new Tile(r, c, element.charAt(0));
                    currMap2.setTile(r, c, newTile);
					
				}
            }
        }
        s.close();

     
	} 
    catch (Exception e) {
        
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
	
		// Find start and goal tiles
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
	
		Queue<Tile> queue = new LinkedList<>();
		Queue<Tile> solutionPath = new LinkedList<>();
		queue.add(start);
		start.setVisited(true);
	
		// Movement directions (North, East, South, West)
		int[][] directions = {
			{-1, 0}, // North
			{0, 1},  // East
			{1, 0},  // South
			{0, -1}  // West
		};
	
		boolean found = false;
	
		while (!queue.isEmpty() && !found) {
			Tile current = queue.poll(); // Dequeue the current tile
	
			// If we found the goal, stop searching
			if (current == goal) {
				found = true;
				solutionPath.add(current);
				break;
			}
	
			boolean moved = false;
	
			// Explore NESW
			for (int[] dir : directions) {
				int newRow = current.getRow() + dir[0];
				int newCol = current.getCol() + dir[1];
				Tile neighbor = activeMap.getTile(newRow, newCol, 0);
	
				if (neighbor != null && (neighbor.getType() == '.' || neighbor == goal) && !neighbor.isVisited()) {
					queue.add(neighbor); // Enqueue the neighbor
					neighbor.setVisited(true);
					moved = true;
					solutionPath.add(current); // Add the current tile to the path
					break;
				}
			}
	
			// If we can't move, remove the tile from the path (backtrack)
			if (!moved) {
				if (!solutionPath.isEmpty()) {
					solutionPath.poll(); // Remove the last tile
				}
			}
		}
	
		// Trace the path from goal to start and mark the solution on the map
		while (!solutionPath.isEmpty()) {
			Tile pathTile = solutionPath.poll();
			if (pathTile != start && pathTile != goal) {
				pathTile.addPath('+');
			}
		}
	
		// Print the maze with the path
		System.out.println(currMap.returnMaze());
	
		runTime();
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

