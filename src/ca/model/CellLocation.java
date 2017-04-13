package ca.model;


/**
 * Represent the coordinates of a game board cell.
 */
public class CellLocation {
	private int x = 0;
	private int y = 0;

	public CellLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public String toString() {
		return getClass().getName() 
				+ "[x=" + x + ", y=" + y 
				+ "]";
	}

	@Override
	public boolean equals(Object otherObject) {
		// Discussion of equals() method found at:
		// http://www.javapractices.com/topic/TopicAction.do?Id=17
		if (otherObject == this) {
			return true;
		}
		if (otherObject == null) {
			return false;
		}
		if (!(otherObject instanceof CellLocation)) {
			return false;
		}
		
		CellLocation other = (CellLocation) otherObject;
		boolean sameX = this.x == other.x;
		boolean sameY = this.y == other.y;
		return sameX && sameY;
	}

	public CellLocation getMovedLocation(MoveDirection move) {
		int newX = x;
		int newY = y;
		
		switch (move) {
		case MOVE_DOWN:  newY++; break;  
		case MOVE_UP:    newY--; break;
		case MOVE_LEFT:  newX--; break;
		case MOVE_RIGHT: newX++; break;
		default:
			assert false;
		}
		
		return new CellLocation(newX, newY);
	}
}
