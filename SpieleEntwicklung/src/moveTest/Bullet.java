package moveTest;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * @author Bogachan
 *
 */
public class Bullet {

	private static BufferedImage look;

	static {
		try {
			look = ImageIO.read(Bullet.class.getClassLoader()
					.getResourceAsStream("grafik/schuss.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private float f_posx;
	private float f_posy;
	private float f_speedx;
	private float f_speedy;
	private float timeAlive;
	private final float TIMETOLIVE = 10;

	private Rectangle bounding;
	private List<Bullet> bullets;

	/**
	 * @param x
	 * @param y
	 * @param speedx
	 * @param speedy
	 * @param bullets
	 */
	public Bullet(float x, float y, float speedx, float speedy,
			List<Bullet> bullets) {
		this.f_posx = x;
		this.f_posy = y;
		this.f_speedx = speedx;
		this.f_speedy = speedy;
		this.bounding = new Rectangle((int) x, (int) y, look.getWidth(),
				look.getHeight());
		this.bullets = bullets;
	}

	/**
	 * @param timeSinceLastFrame
	 */
	public void update(float timeSinceLastFrame) {
		this.timeAlive += timeSinceLastFrame;
		if (timeAlive > TIMETOLIVE) {
			this.bullets.remove(this);
		}
		this.f_posx += this.f_speedx * timeSinceLastFrame;
		this.f_posy += this.f_speedy * timeSinceLastFrame;
		this.bounding.x = (int) this.f_posx;
		this.bounding.y = (int) this.f_posy;
	}

	/**
	 * @return the bounding
	 */
	public Rectangle getBounding() {
		return this.bounding;
	}

	/**
	 * @return the look
	 */
	public static BufferedImage getLook() {
		return look;
	}
}
