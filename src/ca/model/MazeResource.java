package ca.model;

/**
 * Class for initializing values for all resources
 * 
 * @author alanzheng
 *
 */
public final class MazeResource {

	// all file paths should be relative to the root of the class path
	public static final String WIN_SOUND_FILE_PATH = "Sound/IFEELGOOD.wav";
	public static final String LOSE_SOUND_FILE_PATH = "Sound/BARK.wav";
	public static final String ABOUT_IMAGE_FILE_PATH = "Images/cookie.png";
	public static final String NEW_GAME_IMAGE_FILE_PATH = "Images/ladybug.png";
	public static final String HELP_IMAGE_FILE_PATH = "Images/suit.png";
	public static final String EMPTY_CELL_SPACE_ICON_IMG_PATH = "Images/space.png";
	public static final String WALL_ICON_IMG_PATH = "Images/wall.png";
	public static final String FOG_ICON_IMG_PATH = "Images/fog.png";
	public static final String COOKIE_ICON_IMG_PATH = "Images/cookie.png";
	public static final String ENEMY_ICON_IMG_PATH = "Images/ladybug.png";
	public static final String USER_ICON_IMG_PATH = "Images/suit.png";
	public static final String DEAD_ICON_IMG_PATH = "Images/dead.png";
	public static final String VALID_MOVE_SOUND_FILE_PATH = "Sound/BOING.wav";
	public static final String INVALID_MOVE_SOUND_FILE_PATH = "Sound/AHOOGA.wav";
	
	// prevent this class from being instantiated
	private MazeResource() {}
}
