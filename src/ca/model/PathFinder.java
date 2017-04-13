package ca.model;

/**
 * Immutable path finding class. Given a 2D array of CellState objects, it
 * computes if there is a path between cell locations.
 *
 */
public class PathFinder {
	private static final int INVALID_COLOUR = -1;
	private static final int START_COLOUR = 0;

	private int[][] regionArray;
	private int width;
	private int height;
	
	public PathFinder(final CellState[][] data) {
		height = data.length;
		width = data[0].length;
		regionArray = new int[height][width];
	
		floodColoursHorizontally(data);
		unifyColoursVertically();
		
//		dumpFloodArray(regionArray, "Unifying done");		
	}
	
	private void floodColoursHorizontally(final CellState[][] data) {
		int nextColour = START_COLOUR;
		for (int y = 0; y < height; y++) {
			int currentColour = INVALID_COLOUR;
			for (int x = 0; x < width; x++) {
				if (data[y][x].isWall()) {
					currentColour = INVALID_COLOUR;
				} else if (currentColour == INVALID_COLOUR) {
					currentColour = nextColour;
					nextColour++;
				}
				regionArray[y][x] = currentColour;				
			}
		}
	}
	
	private void unifyColoursVertically() {
		// If a cell and one below it are not walls, then both should be the same colour.
		for (int y = 0; y < height - 1; y++) {
			for (int x = 0; x < width; x++) {
				int topColour = regionArray[y][x];
				int bottomColour = regionArray[y + 1][x];
				if (topColour != INVALID_COLOUR && bottomColour != INVALID_COLOUR) {
					replaceAllWith(regionArray, bottomColour, topColour);					
				}
			}
		}
	}
	
	private void replaceAllWith(int[][] data, int replace, int with) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (data[y][x] == replace) {
					data[y][x] = with;
				}
			}
		}
	}
	
	public boolean hasPath(CellLocation cell1, CellLocation cell2) {
		// There exists a path between the start and the end iff they are 
		// the same colour.
		int startColour = regionArray[cell1.getY()][cell1.getX()];
		int endColour   = regionArray[cell2.getY()][cell2.getX()];
		return (startColour == endColour);
	}

	@SuppressWarnings("unused")
	private void dumpFloodArray(int[][] data, String message) {
		System.out.println("Flood fill algorithm: " + message);
		for (int y = 0; y < data.length; y++) {
			for (int x = 0; x < data[0].length; x++) {
				System.out.printf("%4d", data[y][x]);
			}
			System.out.println();
		}
		
	}
}
