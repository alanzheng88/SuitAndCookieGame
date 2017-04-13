package ca.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.model.CellLocation;
import ca.model.CellState;
import ca.model.MazeGame;
import ca.model.MazeResource;
import ca.model.ResourceHelper;

/**
 * Launch the Maze Game with a Graphics UI.
 * 
 * @author alanzheng
 *
 */
public class MazeGUI {

	private static URL winSoundUrl;
	private static URL loseSoundUrl;
	private MazeGame game;
	private int width = 800;
	private int height = 800;
	private JFrame frame;
	private JPanel mazePanel;
	private MovementController keyboard;

	public MazeGUI(MazeGame game) {
		this.game = game;
		winSoundUrl = ResourceHelper.getResourceFile(MazeResource.WIN_SOUND_FILE_PATH);
		loseSoundUrl = ResourceHelper.getResourceFile(MazeResource.LOSE_SOUND_FILE_PATH);
	}

	public void startGame() {
		setupPlayerMove();
		trackGameStatus();
		trackCatMoves();
		trackGameStatus();
		subscribeToDisplayUpdates(false);
		displayGame();
	}

	private void displayGame() {
		frame = new JFrame();
		frame.setTitle("Maze Game");
		frame.setLayout(new BorderLayout());
		frame.setPreferredSize(new Dimension(width, height));

		frame.add(makeButtonPanel(), BorderLayout.NORTH);
		frame.add(makeMazeLayer(false), BorderLayout.CENTER);
		frame.add(makeCountLayer(), BorderLayout.SOUTH);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private JPanel makeCountLayer() {
		JPanel countPanel = new JPanel();
		countPanel.setFocusable(false);
		countPanel.setLayout(new BoxLayout(countPanel, BoxLayout.LINE_AXIS));
		countPanel.add(Box.createHorizontalGlue());
		final JLabel cookiesCollectedText = new JLabel();
		cookiesCollectedText.setText("Collected "
				+ game.getNumberCheeseCollected() + " of "
				+ game.getNumberCheeseToCollect() + " cookies.");
		game.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				cookiesCollectedText.setText("Collected "
						+ game.getNumberCheeseCollected() + " of "
						+ game.getNumberCheeseToCollect() + " cookies.");
			}
		});
		countPanel.add(cookiesCollectedText);
		return countPanel;
	}

	private Component makeButtonPanel() {
		JPanel buttonRow = new JPanel();
		buttonRow.setLayout(new BoxLayout(buttonRow, BoxLayout.LINE_AXIS));
		buttonRow.add(makeNewGameButton("New Game"));
		buttonRow.add(makeHelpButton("Help"));
		buttonRow.add(Box.createHorizontalGlue());
		buttonRow.add(makeAboutButton("About..."));
		keyboard.registerKeyPresses(buttonRow);
		return buttonRow;
	}

	private Component makeNewGameButton(String text) {
		JButton button = makeButton(text);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "Do you really want to abandon this current game?";
				String title = "New Game?";
				ImageIcon picture = getScaleImageIcon(new ImageIcon(
						ResourceHelper.getImage(MazeResource.NEW_GAME_IMAGE_FILE_PATH)), 70, 70);
				int result = JOptionPane.showConfirmDialog(frame, message, title,
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, picture);
				if(result == JOptionPane.YES_OPTION) {
					restartGame();
				}
			}
		});
		return button;
	}

	private Component makeHelpButton(String text) {
		JButton button = makeButton(text);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String helpMessage = "Welcome to Coder's Dreamland!"
						+ "\nYou are the suit touring the maze"
						+ "\nand trying to collect the cookies."
						+ "\nHowever, all your past bad code is"
						+ "\nout to haunt you! These bugs are"
						+ "\nrandomly exploring the maze to kill you!"
						+ "\n\nUse the arrow keys to move around!"
						+ "\n\nBEWARE BAD CODE!";
				ImageIcon picture = getScaleImageIcon(new ImageIcon(
						ResourceHelper.getImage(MazeResource.HELP_IMAGE_FILE_PATH)), 70, 70);
				JOptionPane.showMessageDialog(frame, helpMessage, "Game Help",
						JOptionPane.INFORMATION_MESSAGE, picture);
			}
		});
		return button;
	}

	private Component makeAboutButton(String text) {
		JButton button = makeButton(text);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String helpMessage = "Game Written by Alan Zheng"
						+ "\nImages taken from Crystal Project Application";
				ImageIcon picture = getScaleImageIcon(new ImageIcon(
						ResourceHelper.getImage(MazeResource.ABOUT_IMAGE_FILE_PATH)), 70, 70);
				JOptionPane.showMessageDialog(frame, helpMessage, "About Game",
						JOptionPane.INFORMATION_MESSAGE, picture);
			}
		});
		return button;
	}

	private JButton makeButton(String text) {
		JButton button = new JButton();
		button.setText(text);
		button.setFocusable(false);
		return button;
	}

	private JPanel makeMazeLayer(boolean revealBoard) {
		mazePanel = new JPanel();
		mazePanel.setLayout(new GridLayout(MazeGame.getMazeHeight(), MazeGame
				.getMazeWidth()));
		for (int y = 0; y < MazeGame.getMazeHeight(); y++) {
			for (int x = 0; x < MazeGame.getMazeWidth(); x++) {
				JLabel imageLabel = new JLabel();
				CellLocation cell = new CellLocation(x, y);
				ImageIcon cellImage = getScaleImageIcon(
						getImageForCell(cell, false), 41, 41);
				imageLabel.setIcon(cellImage);
				mazePanel.add(imageLabel);
			}
		}
		return mazePanel;
	}

	private void subscribeToDisplayUpdates(final boolean revealBoard) {
		game.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				redrawUi(false);
			}
		});
	}

	private void redrawUi(boolean revealBoard) {
		mazePanel.removeAll();
		for (int y = 0; y < MazeGame.getMazeHeight(); y++) {
			for (int x = 0; x < MazeGame.getMazeWidth(); x++) {
				JLabel imageLabel = new JLabel();
				CellLocation cell = new CellLocation(x, y);
				ImageIcon cellImage = getScaleImageIcon(
						getImageForCell(cell, revealBoard), 41, 41);
				imageLabel.setIcon(cellImage);
				mazePanel.add(imageLabel);
				mazePanel.revalidate();
				mazePanel.repaint();
			}
		}
	}
	
	private ImageIcon getImageForCell(CellLocation cell, boolean revealBoard) {
		CellState state = game.getCellState(cell);
		
		if (game.isMouseAtLocation(cell) && game.isCatAtLocation(cell)) {
			return new ImageIcon(ResourceHelper.getImage(MazeResource.DEAD_ICON_IMG_PATH));
		} else if (game.isMouseAtLocation(cell)) {
			return new ImageIcon(ResourceHelper.getImage(MazeResource.USER_ICON_IMG_PATH));
		} else if (game.isCatAtLocation(cell)) {
			return new ImageIcon(ResourceHelper.getImage(MazeResource.ENEMY_ICON_IMG_PATH));
		} else if (game.isCheeseAtLocation(cell)) {
			return new ImageIcon(ResourceHelper.getImage(MazeResource.COOKIE_ICON_IMG_PATH));
		} else if (state.isHidden() && !revealBoard) {
			return new ImageIcon(ResourceHelper.getImage(MazeResource.FOG_ICON_IMG_PATH));
		} else if (state.isWall()) {
			return new ImageIcon(ResourceHelper.getImage(MazeResource.WALL_ICON_IMG_PATH));
		} else {
			return new ImageIcon(ResourceHelper.getImage(MazeResource.EMPTY_CELL_SPACE_ICON_IMG_PATH));
		}
	}

	private void setupPlayerMove() {
		keyboard = new MovementController(game);
	}

	private void trackCatMoves() {
		game.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				game.doCatMoves();
			}
		});
	}

	private void trackGameStatus() {
		game.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (game.hasUserWon()) {
					ResourceHelper.playSound(winSoundUrl);
					revealBoard();
					endGame("Congratulations! You won. Play again?");
				} else if (game.hasUserLost()) {
					ResourceHelper.playSound(loseSoundUrl);
					revealBoard();
					endGame("Sorry. You lost. Try again?");
				} else {
					assert false;
				}
			}
		});
	}

	private void endGame(String message) {
		int result = JOptionPane.showConfirmDialog(frame, message, "Game Over", JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION) {
			restartGame();
		} else {
			System.exit(0);
		}
	}

	private void restartGame() {
		frame.dispose();
		game = new MazeGame();
		startGame();
	}
	
	private void revealBoard() {
		redrawUi(true);
	}

	private static ImageIcon getScaleImageIcon(ImageIcon icon, int width,
			int height) {
		return new ImageIcon(getScaledImage(icon.getImage(), width, height));
	}

	private static Image getScaledImage(Image srcImg, int width, int height) {
		BufferedImage resizedImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, width, height, null);
		g2.dispose();
		return resizedImg;
	}
	
}
