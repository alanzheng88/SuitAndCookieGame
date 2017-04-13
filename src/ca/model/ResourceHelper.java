package ca.model;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Helper class for managing resource retrieval
 * and execution
 * 
 * @author alanzheng
 *
 */
public class ResourceHelper {

	private static final ClassLoader CLASS_LOADER = ResourceHelper.class.getClassLoader();
	
	public static URL getResourceFile(String relativeFilePath) {
		return CLASS_LOADER.getResource(relativeFilePath);
	}
	
	public static Image getImage(String relativeImgPath) {
		// class.getResource equivalent to class.getClassLoader.getResource
		// but the first allows paths that start with a forward slash
		// and starts from the root of the classpath
		return Toolkit.getDefaultToolkit()
				.getImage(CLASS_LOADER.getResource(relativeImgPath));
	}
	
	public static void playSound(URL soundUrl) {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(soundUrl);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
