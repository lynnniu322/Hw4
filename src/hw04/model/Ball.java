package hw04.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import provided.utils.dispatcher.IDispatcher;
import provided.utils.dispatcher.IObserver;
import provided.utils.displayModel.IDimension;

/**
 * Abstract representation of a ball
 */
public class Ball implements IObserver<IBallCmd>, IBall {

	/**
	 * location of ball
	 */
	protected Point loc;

	/**
	 * color of ball
	 */
	protected Color color;

	/**
	 * Initial diameter of ball
	 */
	protected int diameter;

	/**
	 * Initial velocity of ball
	 */
	protected Point velocity;

	/**
	 * Boolean indicating if the ball touches the walls
	 */
	protected Boolean bounced = false;

	/**
	 * Initial dimension of ball on canvas
	 */
	protected IDimension dimension;

	/**
	 * Strategy that the ball implements
	 */
	protected IUpdateStrategy updateStrategy;

	/**
	 * The dimension of the ball
	 */
	protected Object getDimension;

	/**
	 * Paint the ball on the given graphics
	 * @param g Graphics 
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(this.color); // Set the color to use when drawing
		g.fillOval((int) this.loc.getX(), (int) this.loc.getY(), this.diameter, this.diameter); // Create the circle with given diameter
	}

	/**
	 * Constructor for abstract ball
	 * @param p location of top left corner of ball
	 * @param d diameter of ball
	 * @param v velocity of ball
	 * @param c color of shape
	 * @param dim dimension of ball on canvas
	 * @param strategy strategy that the ball implements
	 */
	public Ball(Point p, int d, Point v, Color c, IDimension dim, IBallAlgo updateStrategy) {
		this.loc = p;
		this.color = c;
		this.diameter = d;
		this.velocity = v;
		this.dimension = dim;
		this.execute(updateStrategy); // Delegate to the method that executes an algo
	}

	/**
	 * Repaint and move the ball
	 */
	@Override
	public void update(IDispatcher<IBallCmd> disp, IBallCmd cmd) {
		cmd.apply(this, disp);
	}

	/**
	 * Get the location of the ball
	 * @return current location of the ball
	 */
	@Override
	public Point getLoc() {
		return this.loc;
	}

	/**
	 * Set the location of the ball
	 * @param loc updated location
	 */
	@Override
	public void setLoc(Point loc) {
		this.loc = loc;
	}

	/**
	 * Get the ball color
	 * @return color color of ball
	 */
	@Override
	public Color getColor() {
		return this.color;
	}

	/**
	 * Color the ball
	 * @param color color of ball
	 */
	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Get the diameter
	 * @return diameter diameter of ball
	 */
	@Override
	public int getDiameter() {
		return this.diameter;
	}

	/**
	 * Resize the ball
	 * @param diameter diameter of ball
	 */
	@Override
	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	/**
	 * Get the velocity
	 * @return velocity velocity of ball
	 */
	@Override
	public Point getVelocity() {
		return this.velocity;
	}

	/**
	 * Change the ball velocity
	 * @param velocity velocity of ball
	 */
	@Override
	public void setVelocity(Point velocity) {
		this.velocity = velocity;
	}

	/**
	 * Get the strategy
	 * @return strategy strategy of ball
	 */
	@Override
	public IUpdateStrategy getUpdateStrategy() {
		return this.updateStrategy;
	}
	
	/**
	 * Get the strategy
	 * @return strategy strategy of ball
	 */
	@Override
	public void setUpdateStrategy(IUpdateStrategy algo) {
		this.updateStrategy = algo;
	}


	/**
	 * move the ball, bouncing if at the edge of the screen
	 */
	@Override
	public void move() {
		this.loc.translate(this.velocity.x, this.velocity.y);
		this.bounceIfNeeded();
	}

	/**
	 * Determine whether the ball needs to bounce, and if so bounces
	 */
	private void bounceIfNeeded() {
		int minX = this.loc.x; // the X value of the left extent of the ball
		int maxX = this.loc.x + this.diameter; // the X value of the right extent of the ball

		int minY = this.loc.y; // the Y value of the top extent of the ball
		int maxY = this.loc.y + this.diameter; // the Y value of the bottom extent of the ball

		this.bounced = false;

		if (minX < 0 && this.velocity.x < 0) { // left bounce
			this.bounced = true;
			this.loc.translate(-2 * minX, 0);
			this.velocity.setLocation(-this.velocity.x, this.velocity.y);
		}
		if (maxX > this.dimension.getWidth() && this.velocity.x > 0) { // right bounce
			this.bounced = true;
			this.loc.translate(2 * (this.dimension.getWidth() - maxX), 0);
			this.velocity.setLocation(-this.velocity.x, this.velocity.y);
		}

		if (minY < 0 && this.velocity.y < 0) { // top bounce
			this.bounced = true;
			this.loc.translate(0, -2 * minY);
			this.velocity.setLocation(this.velocity.x, -this.velocity.y);
		}
		if (maxY > this.dimension.getHeight() && this.velocity.y > 0) { // bottom bounce
			this.bounced = true;
			this.loc.translate(0, 2 * (this.dimension.getHeight() - maxY));
			this.velocity.setLocation(this.velocity.x, -this.velocity.y);
		}
	}

	/**
	 * @return the dimension of the ball
	 */
	@Override
	public IDimension getDimension() {
		return this.dimension;
	}

	/**
	 * @return the boolean indicating if the ball touches the walls
	 */
	@Override
	public Boolean getBounced() {
		return this.bounced;
	}
	
	/**
	 * {@inheritDoc}
	 * <br>
	 * Runs the default case of the algorithm.
	 */
	@Override
	public void execute(IBallAlgo algo, IDispatcher<IBallCmd> disp) {
		algo.caseDefault(this);
	}


}