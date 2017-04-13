package ca.ui;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import ca.model.MazeGame;
import ca.model.MazeResource;
import ca.model.MoveDirection;
import ca.model.ResourceHelper;

/**
 * Handles all key presses by the user
 * and notifies other classes of changes
 * @author alanzheng
 *
 */
public class MovementController {

	private static final String[] KEYS = { "UP", "DOWN", "LEFT", "RIGHT" };
	private static URL validMoveSoundUrl;
	private static URL invalidMoveSoundUrl;
	private MazeGame game;

	public MovementController(MazeGame game) {
		this.game = game;
		validMoveSoundUrl = ResourceHelper.getResourceFile(MazeResource.VALID_MOVE_SOUND_FILE_PATH);
		invalidMoveSoundUrl = ResourceHelper.getResourceFile(MazeResource.INVALID_MOVE_SOUND_FILE_PATH);
	}

	public void registerKeyPresses(JComponent c) {
		for (int i = 0; i < KEYS.length; i++) {
			// get up down left right
			String key = KEYS[i];
			// string maps to some object key
			c.getInputMap().put(KeyStroke.getKeyStroke(key), key);
			// associate listener with event
			c.getActionMap().put(key, makeNextPlayerMove(key));
		}
	}

	@SuppressWarnings("serial")
	public AbstractAction makeNextPlayerMove(final String move) {
		return new AbstractAction() {
			public void actionPerformed(ActionEvent evt) {
				MoveDirection possibleMove = MoveDirection.MOVE_NONE;
				switch (move) {
				case "UP":
					possibleMove = MoveDirection.MOVE_UP;
					break;
				case "DOWN":
					possibleMove = MoveDirection.MOVE_DOWN;
					break;
				case "LEFT":
					possibleMove = MoveDirection.MOVE_LEFT;
					break;
				case "RIGHT":
					possibleMove = MoveDirection.MOVE_RIGHT;
					break;
				default:
					assert false;
					break;
				}
				if (!game.isValidPlayerMove(possibleMove)) {
					ResourceHelper.playSound(invalidMoveSoundUrl);
				} else {
					game.recordPlayerMove(possibleMove);
					ResourceHelper.playSound(validMoveSoundUrl);
					game.notifyAllListeners();
				}
			}
		};
	}
}