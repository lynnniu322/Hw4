package hw04.model.paintStrategies;

import java.awt.geom.AffineTransform;

public class FungiPaintStrategy extends MultiPaintStrategy {

	/**
	 * @param at
	 * @param paintStrategies
	 */
	public FungiPaintStrategy(AffineTransform at, APaintStrategy... paintStrategies) {
		super(at, paintStrategies);
	}
	
	/**
	 * Default constructor
	 */
	public FungiPaintStrategy() {
		super(new AffineTransform(), new EllipsePaintStrategy(),
				new RectanglePaintStrategy(new AffineTransform(), 0, 1.5 / 3.0, 2.0 / 4.0, 2.0 / 3.0));

	}

}
