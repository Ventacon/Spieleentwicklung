package moveTest;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * @author Bogachan
 *
 */
public class Player {

	private Rectangle bounding;
	private float f_posx;
	private float f_posy;
	private int worldsize_x;
	private int worldsize_y;
	private BufferedImage grafik;
	private BufferedImage grafik_dead;
	private List<Bullet> bullets;
	private List<Enemy> enemies;
	private float timeSinceLastShot = 0;
	private final float SHOTFREQUENCY = 0.1f;
	private boolean alive = true;

	/**
	 * @param x
	 * @param y
	 * @param worldsize_x
	 * @param worldsize_y
	 * @param bullets
	 */
	public Player(int x, int y, int worldsize_x, int worldsize_y,
			List<Bullet> bullets, List<Enemy> enemies) {
		try {
			this.grafik = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("grafik/raumschiff.png"));
			this.grafik_dead = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("grafik/raumschiff_kaputt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.bounding = new Rectangle(x, y, this.grafik.getWidth(),
				this.grafik.getHeight());

		this.f_posx = x;
		this.f_posy = y;
		this.worldsize_x = worldsize_x;
		this.worldsize_y = worldsize_y;
		this.bullets = bullets;
		this.enemies = enemies;

	}

	/**
	 * @param timeSinceLastFrame
	 */
	public void update(float timeSinceLastFrame) {
		if (!this.alive) {
			return;
		}
		this.timeSinceLastShot += timeSinceLastFrame;
		if (Keyboard.isKeyDown(KeyEvent.VK_W)) {
			this.f_posy -= 400 * timeSinceLastFrame;
		}
		if (Keyboard.isKeyDown(KeyEvent.VK_S)) {
			this.f_posy += 400 * timeSinceLastFrame;
		}
		if (Keyboard.isKeyDown(KeyEvent.VK_D)) {
			this.f_posx += 400 * timeSinceLastFrame;
		}
		if (Keyboard.isKeyDown(KeyEvent.VK_A)) {
			this.f_posx -= 400 * timeSinceLastFrame;
		}

		if (this.timeSinceLastShot > this.SHOTFREQUENCY
				&& Keyboard.isKeyDown(KeyEvent.VK_SPACE)) {
			this.timeSinceLastShot = 0;
			this.bullets.add(new Bullet(this.f_posx + this.grafik.getWidth()
					/ 2 - Bullet.getLook().getWidth() / 2, this.f_posy
					+ this.grafik.getHeight() / 2
					- Bullet.getLook().getHeight() / 2, 500, 0, bullets));
		}

		if (this.f_posx < 0) {
			this.f_posx = 0;
		}
		if (this.f_posy < 0) {
			this.f_posy = 0;
		}

		if (this.f_posx > this.worldsize_x - this.bounding.width) {
			this.f_posx = this.worldsize_x - this.bounding.width;
		}
		if (this.f_posy > this.worldsize_y - this.bounding.height) {
			this.f_posy = this.worldsize_y - this.bounding.height;
		}

		this.bounding.x = (int) this.f_posx;
		this.bounding.y = (int) this.f_posy;

		for (int i = 0; i < this.enemies.size(); i++) {
			Enemy e = this.enemies.get(i);
			if (e.isAlive() && this.bounding.intersects(e.getBounding())) {
				this.alive = false;
			}

		}
	}

	/**
	 * @return the bounding
	 */
	public Rectangle getBounding() {
		return this.bounding;
	}

	/**
	 * @return the grafik
	 */
	public BufferedImage getGrafik() {
		if (this.alive) {
			return this.grafik;
		} else {
			return this.grafik_dead;
		}
	}

	/**
	 * @return the f_posx
	 */
	public float getF_posx() {
		return this.f_posx;
	}

	/**
	 * @return the f_posy
	 */
	public float getF_posy() {
		return this.f_posy;
	}
}
