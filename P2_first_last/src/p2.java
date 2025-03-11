import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class p2 {
	
	static boolean Stack = false;
	static boolean Queue = false;
	static boolean Opt = false;
	static boolean Time = false;
	static boolean Incoordinate = false;
	static boolean Outcoordinate = false;
	static boolean Help = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readMap("TEST01");

		
	}
	
	public static void readMap(String filename) {
		try {
			File file = new File(filename);
			Scanner s = new Scanner(file);
			
			int numRows = s.nextInt();
			int numCols = s.nextInt();
			int numsRooms = s.nextInt();
			
			int rowIndex = 0;
			
			while(s.hasNextLine()) {
				String row = s.nextLine();
				
				if(row.length() > 0) {
					for(int i = 0; i < numCols && i < row.length(); i++ ) {
						char el = row.charAt(i);
						Tile obj = new Tile(rowIndex, i , el);
						
						
					}
				}
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
}

