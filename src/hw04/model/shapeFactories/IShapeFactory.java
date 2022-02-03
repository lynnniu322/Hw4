package hw04.model.shapeFactories;

import java.awt.Shape;

/**
 * @author yihan
 * Abstract factory that creates a Shape for use as prototype shapes in IPaintStrategies.  
 * The location of the center of the shape and the x and y scales can be specified.
 *
 */
public interface IShapeFactory {
	/**
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param xScale the scaling factor of the x direction
	 * @param yScale the scaling factor of the y direction
	 * @return the shape class
	 */
	public abstract Shape makeShape(double x, double y, double xScale, double yScale);
}
