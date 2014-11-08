package moveTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author Bogachan
 *
 */
public class Frame extends JFrame {

	private BufferStrategy strategy;

	final Player player;
	final Background background;

	private List<Bullet> bullets;
	private List<Enemy> enemies;

	/**
	 * @param player
	 * @param background
	 * @param bullets
	 * 
	 */
	public Frame(Player player, Background background, List<Bullet> bullets,
			List<Enemy> enemies) {
		super("Move Test");
		this.addKeyListener(new Keyboard());
		this.player = player;
		this.background = background;
		this.bullets = bullets;
		this.enemies = enemies;
	}

	/**
	 * 
	 */
	public void makeStrategy() {
		this.createBufferStrategy(2);
		this.strategy = getBufferStrategy();
	}

	/**
	 * 
	 */
	public void repaintScreen() {
		Graphics g = this.strategy.getDrawGraphics();
		this.draw(g);
		g.dispose();
		this.strategy.show();
	}

	private void draw(Graphics g) {
		g.setColor(Color.RED);
		// Zeichnet Hintergrund
		g.drawImage(this.background.getGrafik(), this.background.getF_posx(),
				0, null);
		g.drawImage(this.background.getGrafik(), this.background.getF_posx()
				+ this.background.getGrafik().getWidth(), 0, null);

		for (int i = 0; i < this.enemies.size(); i++) {
			Enemy e = this.enemies.get(i);
			g.drawImage(e.getLook(), e.getBounding().x, e.getBounding().y, null);

		}

		for (int i = 0; i < this.bullets.size(); i++) {
			Bullet b = this.bullets.get(i);
			g.drawImage(Bullet.getLook(), b.getBounding().x, b.getBounding().y,
					null);
		}

		// Zeichnet ein Rechteck
		g.drawImage(player.getGrafik(), player.getBounding().x,
				player.getBounding().y, null);

	}

}
