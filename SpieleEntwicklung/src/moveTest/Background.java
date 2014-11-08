package moveTest;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Bogachan
 *
 */
public class Background {
	private float f_posx;
	private float f_speed;
	private BufferedImage grafik;

	/**
	 * @param f_speed
	 */
	public Background(float f_speed) {
		this.f_speed = f_speed;

		try {
			this.grafik = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("grafik/galaxy.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param timeSinceLastFrame
	 */
	public void update(float timeSinceLastFrame) {
		this.f_posx -= this.f_speed * timeSinceLastFrame;

		if (this.f_posx < -this.grafik.getWidth()) {
			this.f_posx += this.grafik.getWidth();
		}
	}

	/**
	 * @return the f_posx
	 */
	public int getF_posx() {
		return (int) this.f_posx;
	}

	/**
	 * @return the grafik
	 */
	public BufferedImage getGrafik() {
		return this.grafik;
	}

}
