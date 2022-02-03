package hw04.model.shapeFactories;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class BallShapeFactory implements IShapeFactory {

	/**
	 * Static factory
	 */
	public static BallShapeFactory Singleton = new BallShapeFactory();

	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		return new Ellipse2D.Double(x - xScale / 2.0, y - yScale / 2.0, xScale, yScale);
	}

}
