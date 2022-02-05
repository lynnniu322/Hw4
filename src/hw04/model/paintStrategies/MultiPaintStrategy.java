package hw04.model.paintStrategies;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import hw04.model.IBall;

/**
 * Multi paint strategies.
 * @author yihan
 *
 */
public class MultiPaintStrategy extends APaintStrategy {

	/**
	 * Different strategies to be painted
	 */
	private APaintStrategy[] paintStrategies;
	
	/**
	 * Constructor with paint strategies to make a composite
	 * @param paintStrategies the set of paint strategies to use.
	 */
	public MultiPaintStrategy(APaintStrategy... paintStrategies) {
		this.paintStrategies = paintStrategies;
	}

	/**
	 * @param at affine transform.
	 * @param paintStrategies the paint strategies.
	 */
	public MultiPaintStrategy(AffineTransform at, APaintStrategy... paintStrategies) {
		super(at);
		this.paintStrategies = paintStrategies;
	}

	@Override
	public void init(IBall host) {
		for (APaintStrategy paintStrategy : paintStrategies) {
			paintStrategy.init(host);
		}

	}

	@Override
	protected void paintCfg(Graphics g, IBall host) {
		super.paintCfg(g, host);
		// keep upright
		if (Math.abs(Math.atan2(host.getVelocity().y, host.getVelocity().x)) > Math.PI / 2.0) {
			at.scale(1.0, -1.0);
		}
	}

	@Override
	public void paintXfrm(Graphics g, IBall host, AffineTransform at) {
		for (APaintStrategy paintStrategy : paintStrategies) {
			paintStrategy.paintXfrm(g, host, this.at);
		}
	}

}
