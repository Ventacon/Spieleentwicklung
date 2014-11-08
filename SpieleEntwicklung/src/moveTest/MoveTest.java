package moveTest;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

/**
 * @author Bogachan
 *
 */
public class MoveTest {

	static List<Bullet> bullets = new LinkedList<Bullet>();
	static List<Enemy> enemies = new LinkedList<Enemy>();
	static Player player = new Player(300, 300, 800, 600, bullets, enemies);
	static Background backgorund = new Background(50);
	static Random r = new Random();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		spawnEnemy();
		Frame f = new Frame(player, backgorund, bullets, enemies);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 600);
		// schaltet alles Rand aus
		f.setUndecorated(true);
		// f.setVisible(true);
		f.setResizable(false);

		// Displaymodus Parameter sind breite, hoehe, Farbtiefe,
		// Bildwiederholfrequenz
		DisplayMode displayMode = new DisplayMode(800, 600, 32, 60);
		// Beinhaltet alle Bildschirme die am PC angeschlossen sind.
		GraphicsEnvironment environment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice device = environment.getDefaultScreenDevice();
		device.setFullScreenWindow(f);
		device.setDisplayMode(displayMode);

		f.makeStrategy();

		final float ENEMYSPAWNTIME = 1f;
		float timeSinceLastEnemySpawn = 0;

		long lastFrame = System.currentTimeMillis();

		while (true) {
			if (Keyboard.isKeyDown(KeyEvent.VK_ESCAPE)) {
				System.exit(0);
			}

			long thisFrame = System.currentTimeMillis();
			float timeSinceLastFrame = ((float) (thisFrame - lastFrame)) / 1000f;
			lastFrame = thisFrame;

			timeSinceLastEnemySpawn += timeSinceLastFrame;
			if (timeSinceLastEnemySpawn > ENEMYSPAWNTIME) {
				timeSinceLastEnemySpawn -= ENEMYSPAWNTIME;
				spawnEnemy();
			}

			player.update(timeSinceLastFrame);
			backgorund.update(timeSinceLastFrame);

			f.repaintScreen();

			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).update(timeSinceLastFrame);
			}

			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).uptade(timeSinceLastFrame);
			}

			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 */
	public static void spawnEnemy() {
		enemies.add(new Enemy(800, r.nextInt(600 - Enemy.getHeight()), bullets,
				player));
	}

}
