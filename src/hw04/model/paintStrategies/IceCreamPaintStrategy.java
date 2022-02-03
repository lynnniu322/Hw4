package hw04.model.paintStrategies;

import java.awt.geom.AffineTransform;

public class IceCreamPaintStrategy extends MultiPaintStrategy {

	/**
	 * @param at
	 * @param paintStrategies
	 */
	public IceCreamPaintStrategy(AffineTransform at, APaintStrategy... paintStrategies) {
		super(at, paintStrategies);
	}

	/**
	 * Default constructor
	 */
	public IceCreamPaintStrategy() {
		super(new AffineTransform(), new BallPaintStrategy(),
				new TrianglePaintStrategy(new AffineTransform(), 0, 3.0/4.0, 1, 3));

	}
}
