package ca.ui;
import ca.model.MazeGame;

/**
 * Launch the Maze game with a Graphics
 * UI
 * @author alanzheng
 *
 */
public class MazeGameGui {

	public static void main(String[] args) {
		MazeGame game = new MazeGame();
		MazeGUI gui = new MazeGUI(game);
		gui.startGame();
	}
}
