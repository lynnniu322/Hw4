package hw04.model.shapeFactories;

import java.awt.Shape;

/**
 * @author yihan
 * Generate a prototype shape.
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
