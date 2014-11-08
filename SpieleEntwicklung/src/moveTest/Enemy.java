package moveTest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * @author Bogachan
 *
 */
public class Enemy {
	private static BufferedImage[] look = new BufferedImage[4];
	private static final float NEEDEDANITIME = 0.75f;
	private static final Random r = new Random();
	private final float SPEED;
	private float aniTime = 0;
	private float posx;
	private float posy;
	private Rectangle bounding;
	private List<Bullet> bullets;
	private static BufferedImage grafik_dead;
	private boolean alive = true;
	private Player player;

	static {
		try {
			look[0] = ImageIO.read(Bullet.class.getClassLoader()
					.getResourceAsStream("grafik/enemy1.png"));
			look[1] = ImageIO.read(Bullet.class.getClassLoader()
					.getResourceAsStream("grafik/enemy2.png"));
			look[2] = ImageIO.read(Bullet.class.getClassLoader()
					.getResourceAsStream("grafik/enemy3.png"));
			look[3] = look[1];
			grafik_dead = ImageIO.read(Bullet.class.getClassLoader()
					.getResourceAsStream("grafik/enemy_kaputt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param bullets
	 * @param player
	 */
	public Enemy(float x, float y, List<Bullet> bullets, Player player) {
		this.posx = x;
		this.posy = y;
		this.bounding = new Rectangle((int) x, (int) y, look[0].getWidth(),
				look[0].getHeight());
		this.bullets = bullets;
		this.player = player;
		this.SPEED = 50 + r.nextInt(101);
	}

	/**
	 * @param timeSinceLastFrame
	 */
	public void uptade(float timeSinceLastFrame) {
		this.aniTime += timeSinceLastFrame;
		if (this.aniTime > NEEDEDANITIME) {
			this.aniTime = 0;
		}

		// this.posx -= 100 * timeSinceLastFrame;
		if (this.alive) {
			// // oben
			// if (this.player.getF_posy() - this.posy < -10) {
			// this.posy -= SPEED * timeSinceLastFrame;
			// }
			// // Unten
			// else if (this.player.getF_posy() - this.posy > 10) {
			// this.posy += SPEED * timeSinceLastFrame;
			// }
			// // Links
			// if (this.player.getF_posx() < this.posx) {
			// this.posx -= SPEED * timeSinceLastFrame;
			// }
			// // Rechts
			// if (this.player.getF_posx() > this.posx) {
			// this.posx += SPEED * timeSinceLastFrame;
			// }

			float speedx = this.player.getF_posx() - this.posx;
			float speedy = this.player.getF_posy() - this.posy;

			float length = (float) Math.sqrt(speedx * speedx + speedy * speedy);

			if (length != 0) {
				speedx /= length;
				speedy /= length;

				speedx *= this.SPEED * timeSinceLastFrame;
				speedy *= this.SPEED * timeSinceLastFrame;

				this.posx += speedx;
				this.posy += speedy;
			}

		}

		if (this.posx <= -this.getLook().getWidth()) {
			this.posx = 800;
			this.posy = r.nextInt(600 - this.getLook().getHeight());
			this.alive = true;

		}

		for (int i = 0; i < this.bullets.size(); i++) {
			Bullet b = this.bullets.get(i);
			if (this.alive && this.bounding.intersects(b.getBounding())) {
				this.alive = false;
				this.bullets.remove(b);
			}
		}
		this.bounding.x = (int) this.posx;
		this.bounding.y = (int) this.posy;
	}

	/**
	 * @return the bounding
	 */
	public Rectangle getBounding() {
		return this.bounding;
	}

	/**
	 * @return
	 */
	public BufferedImage getLook() {
		if (!this.alive) {
			return grafik_dead;
		} else {
			if (look.length == 0) {
				return null;
			}
			for (int i = 0; i < look.length; i++) {
				if (this.aniTime < (float) Enemy.NEEDEDANITIME / look.length
						* (i + 1)) {
					return look[i];
				}
			}
			return look[look.length - 1];
		}

	}

	/**
	 * @return the alive
	 */
	public boolean isAlive() {
		return this.alive;
	}

	/**
	 * @return
	 */
	public static int getWidth() {
		return look[0].getWidth();
	}

	/**
	 * @return
	 */
	public static int getHeight() {
		return look[0].getHeight();
	}
}
