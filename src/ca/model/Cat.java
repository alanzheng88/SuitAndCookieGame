package ca.model;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The position and motion control of a cat.
 */
public class Cat {
	private MazeGame game;
	private CellLocation location;
	private MoveDirection lastMove = MoveDirection.MOVE_NONE;
	
	public Cat(MazeGame game, CellLocation location) {
		this.game = game;
		this.location = location;
	}
	
	public CellLocation getLocation() {
		return location;
	}

	public void doMove() {
		ArrayList<MoveDirection> possibleMoves = getPossibleMoves();
		pickOKMove(possibleMoves);		
		// Once moved, the game will ask where the cat is, as needed.
	}

	private ArrayList<MoveDirection> getPossibleMoves() {
		ArrayList<MoveDirection> directions = new ArrayList<MoveDirection>();
		directions.add(MoveDirection.MOVE_UP);
		directions.add(MoveDirection.MOVE_DOWN);
		directions.add(MoveDirection.MOVE_RIGHT);
		directions.add(MoveDirection.MOVE_LEFT);
		
		// Have the cat try not to back-track unless needed by making the 
		// backtracking move (opposite last move) be the last one to try.
		MoveDirection oppositeLastMove = lastMove.getOppositeMove();
		directions.remove(oppositeLastMove);
		Collections.shuffle(directions);
		directions.add(oppositeLastMove);
		return directions;
	}

	private void pickOKMove(ArrayList<MoveDirection> directions) {
		for (MoveDirection move : directions) {
			CellLocation targetLocation = location.getMovedLocation(move);
			if (game.isCellOpen(targetLocation)) {
				location = targetLocation;
				lastMove = move;
				return;
			}
		}
	}	
}
