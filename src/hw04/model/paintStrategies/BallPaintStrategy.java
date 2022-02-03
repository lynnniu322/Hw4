package hw04.model.paintStrategies;

import java.awt.geom.AffineTransform;

/**
 * Paint strategy for ball.
 * @author yihan
 *
 */
public class BallPaintStrategy extends EllipsePaintStrategy {

	/**
	 * No parameter constructor that creates a prototype circle with radius of 1.
	 * An AffineTranform for internal use is instantiated.
	 */
	public BallPaintStrategy() {
		this(new AffineTransform(), 0.0d, 0.0d, 1.0d);
	}

	/**
	 * Constructor that allows the specification of the location, radius
	 * of the prototype circle.   The AffineTransform to use is given.
	 * @param at The AffineTransform to use for internal calculations
	 * @param x floating point x-coordinate of center of circle
	 * @param y floating point y-coordinate of center of circle
	 * @param radius floating point radius of the circle
	 */
	public BallPaintStrategy(AffineTransform at, double x, double y, double radius) {
		super(at, x, y, radius, radius);
	}

}
