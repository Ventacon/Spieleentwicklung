package moveTest;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Bogachan
 *
 */
public class Keyboard implements KeyListener {

	private static boolean[] keys = new boolean[1024];

	/**
	 * @param keyCode
	 * @return
	 */
	public static boolean isKeyDown(int keyCode) {
		if (keyCode >= 0 && keyCode < keys.length) {
			return keys[keyCode];
		} else {
			return false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode >= 0 && keyCode < keys.length) {
			keys[keyCode] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode >= 0 && keyCode < keys.length) {
			keys[keyCode] = false;
		}
	}

	// Unnoetig
	@Override
	public void keyTyped(KeyEvent e) {
	}

}
