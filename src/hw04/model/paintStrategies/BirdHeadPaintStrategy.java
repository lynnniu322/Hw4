package hw04.model.paintStrategies;

import java.awt.geom.AffineTransform;

/**
 * The paint strategy that paints an ice cream
 *
 */
public class BirdHeadPaintStrategy extends MultiPaintStrategy {

	/**
	 * @param at Affine Transform
	 * @param paintStrategies the paint strategues that compose an ice cream
	 */
	public BirdHeadPaintStrategy(AffineTransform at, APaintStrategy... paintStrategies) {
		super(at, paintStrategies);
	}

	/**
	 * Default constructor
	 */
	public BirdHeadPaintStrategy() {
		super(new AffineTransform(), new BallPaintStrategy(),
				new TrianglePaintStrategy(new AffineTransform(), 0, 3.0 / 4.0, 1, 3));

	}
}
