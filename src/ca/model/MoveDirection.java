package ca.model;

/**
 * Encode the possible move directions.
 */
public enum MoveDirection {
	MOVE_NONE,
	MOVE_UP,
	MOVE_RIGHT,
	MOVE_DOWN,
	MOVE_LEFT;

	public MoveDirection getOppositeMove() {
		switch (this) {
		case MOVE_UP:    return MOVE_DOWN;
		case MOVE_DOWN:  return MOVE_UP;
		case MOVE_LEFT:  return MOVE_RIGHT;
		case MOVE_RIGHT: return MOVE_LEFT;
		default:
			return MOVE_NONE;
		}
	}
}