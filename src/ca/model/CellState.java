package ca.model;

/**
 * Store the state of a game-board cell.
 * An immutable class.
 */
public class CellState {
	private boolean isVisibile = false;
	private boolean isWall = false;
	
	public CellState(boolean isWall) {
		this.isWall = isWall;
	}
	public CellState(boolean isWall, boolean isVisibile) {
		this.isWall = isWall;
		this.isVisibile = isVisibile;
	}
	
	public boolean isWall() {
		return isWall;
	}
	
	public boolean isVisible() {
		return isVisibile;
	}
	public boolean isHidden() {
		return !isVisibile;
	}
	
	// Create new instance based on current state (Immutable)
	public CellState makeVisible() {
		return new CellState(isWall, true);
	}
}
