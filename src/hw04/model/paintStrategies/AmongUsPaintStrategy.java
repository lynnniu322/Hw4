package hw04.model.paintStrategies;

import java.awt.geom.AffineTransform;

public class AmongUsPaintStrategy extends ImagePaintStrategy {

	/**
	 * @param at
	 */
	public AmongUsPaintStrategy(AffineTransform at) {
		super(at);
		this.loadImg("images/AmongUs.png");
		this.fillFactor = 0.9d;
	}

	/**
	 * Default constructor.
	 */
	public AmongUsPaintStrategy() {
		this(new AffineTransform());
	}

}
