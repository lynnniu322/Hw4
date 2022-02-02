package hw04.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import hw04.model.paintStrategies.IPaintStrategy;
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
	 * Initial radius of ball
	 */
	protected int radius;

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
	protected IBallAlgo algo;
	
	protected IUpdateStrategy updateStrat = IUpdateStrategy.NULL;
	
	protected IPaintStrategy paintStrat = IPaintStrategy.NULL;
	
	

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
		g.fillOval((int) this.loc.getX(), (int) this.loc.getY(), this.radius*2, this.radius*2); // Create the circle with given diameter
	}

	/**
	 * Constructor for abstract ball
	 * @param p location of top left corner of ball
	 * @param r radius of ball
	 * @param v velocity of ball
	 * @param c color of shape
	 * @param dim dimension of ball on canvas
	 * @param algo 
	 */
	public Ball(Point p, int r, Point v, Color c, IDimension dim, IBallAlgo algo) {
		this.loc = p;
		this.color = c;
		this.radius = r;
		this.velocity = v;
		this.dimension = dim;
		this.algo = algo; // Delegate to the method that executes an algo
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
	public int getRadius() {
		return this.radius;
	}

	/**
	 * Resize the ball
	 * @param r radius of ball
	 */
	@Override
	public void setRadius(int r) {
		this.radius = r;
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
	 * Get the IBallAlgo.
	 * @return algo of ball
	 */
	@Override
	public IBallAlgo getAlgo() {
		return this.algo;
	}
	
	/**
	 * Set update strategy.
	 */
	@Override
	public void setUpdateStrategy(IUpdateStrategy updateStrat) {
		this.updateStrat = updateStrat;
	}
	
	/**
	 * Get the strategy.
	 * @return strategy strategy of ball
	 */
	@Override
	public IUpdateStrategy getUpdateStrategy() {
		return this.updateStrat;
	}


	/**
	 * move the ball, bouncing if at the edge of the screen
	 */
	@Override
	public void move() {
		this.loc.translate(this.velocity.x, this.velocity.y);
		//this.bounceIfNeeded();
		this.bounce();
	}

//	/**
//	 * Determine whether the ball needs to bounce, and if so bounces
//	 */
//	private void bounceIfNeeded() {
//		int minX = this.loc.x; // the X value of the left extent of the ball
//		int maxX = this.loc.x + this.radius*2; // the X value of the right extent of the ball
//
//		int minY = this.loc.y; // the Y value of the top extent of the ball
//		int maxY = this.loc.y + this.radius*2; // the Y value of the bottom extent of the ball
//
//		this.bounced = false;
//
//		if (minX < 0 && this.velocity.x < 0) { // left bounce
//			this.bounced = true;
//			this.loc.translate(-2 * minX, 0);
//			this.velocity.setLocation(-this.velocity.x, this.velocity.y);
//		}
//		if (maxX > this.dimension.getWidth() && this.velocity.x > 0) { // right bounce
//			this.bounced = true;
//			this.loc.translate(2 * (this.dimension.getWidth() - maxX), 0);
//			this.velocity.setLocation(-this.velocity.x, this.velocity.y);
//		}
//
//		if (minY < 0 && this.velocity.y < 0) { // top bounce
//			this.bounced = true;
//			this.loc.translate(0, -2 * minY);
//			this.velocity.setLocation(this.velocity.x, -this.velocity.y);
//		}
//		if (maxY > this.dimension.getHeight() && this.velocity.y > 0) { // bottom bounce
//			this.bounced = true;
//			this.loc.translate(0, 2 * (this.dimension.getHeight() - maxY));
//			this.velocity.setLocation(this.velocity.x, -this.velocity.y);
//		}
//	}
	
	/**
	 * Bounce the ball if needed.
	 */
	public void bounce() {
		this.loc = new Point(bounceHelper(this.loc.x, this.radius, dimension.getWidth() - this.radius, true),
				bounceHelper(this.loc.y, this.radius, dimension.getHeight() - this.radius, false));
	}

	/**
	 * Calculate the position after bouncing in 1 dimension.
	 * @param coord current x or y coordinate of the ball
	 * @param low lower bound of that coordinate
	 * @param high higher bound of that coordinate
	 * @param isX true if it's for x-coordinate, false if for y-coordinate
	 * @return new position after bouncing
	 */
	protected int bounceHelper(int coord, int low, int high, boolean isX) {
		if (coord <= high && coord >= low) {
			// within canvas
			return coord;
		} else {
			if (isX) {
				this.velocity.x *= -1;
			} else {
				this.velocity.y *= -1;
			}
			if (coord > high) {
				return high - (coord - high);
			} else {
				return low + (low - coord);
			}
		}

	}

	/**
	 * @return the dimension of the canvas.
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
	public void execute(IBallAlgo algo) {
		algo.caseDefault(this);
		
	}

	@Override
	public void setAlgo(IBallAlgo algo) {
		this.algo = algo;
		
	}

	@Override
	public void setPaintStrategy(IPaintStrategy iPaintStrategy) {
		this.paintStrat = iPaintStrategy;
		this.paintStrat.init(this);
	}
	

	@Override
	public IPaintStrategy getPaintStrategy() {
		return this.paintStrat;
	}


}