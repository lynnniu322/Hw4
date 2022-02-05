package hw04.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import hw04.model.paintStrategies.IPaintStrategy;
import hw04.model.updateStrategies.IUpdateStrategy;
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
	 * Ball to model adapter, used to talk to model and get canvas size with model2view adapter.
	 */
	protected IBall2ModelAdapter ball2ModelAdapter;

	/**
	 * Dimension of the canvas.
	 */
	protected IDimension dimension;

	/**
	 * Strategy that the ball implements
	 */
	protected IBallAlgo algo;

	/**
	 * The update strategy of the ball
	 */
	protected IUpdateStrategy updateStrat = IUpdateStrategy.NULL;

	/**
	 * The paint strategy of the ball
	 */
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
		g.fillOval((int) this.loc.getX() - this.radius, (int) this.loc.getY() - this.radius, this.radius * 2,
				this.radius * 2); // Create the circle with given diameter
	}

	/**
	 * Constructor for abstract ball
	 * @param p location of top left corner of ball
	 * @param r radius of ball
	 * @param v velocity of ball
	 * @param c color of shape
	 * @param dim dimension of canvas
	 * @param adapter Ball2ModelAdapter
	 * @param algo the algorithm implemented by the ball
	 */
	public Ball(Point p, int r, Point v, Color c, IDimension dim, IBall2ModelAdapter adapter, IBallAlgo algo) {
		this.loc = p;
		this.color = c;
		this.radius = r;
		this.velocity = v;
		this.ball2ModelAdapter = adapter;
		this.dimension = dim;
		this.execute(algo); // Delegate to the method that executes an algo

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
		this.getUpdateStrategy().init(this);
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
		this.bounceIfNeeded();
		//this.bounce();
	}

	/**
	 * Determine whether the ball needs to bounce, and if so bounces
	 */
	private void bounceIfNeeded() {
		int minX = this.loc.x - radius; // the X value of the left extent of the ball
		int maxX = this.loc.x + radius; // the X value of the right extent of the ball

		int minY = this.loc.y - radius; // the Y value of the top extent of the ball
		int maxY = this.loc.y + radius; // the Y value of the bottom extent of the ball

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

	
	@Override
	public IBall2ModelAdapter getBall2ModelAdapter() {
		return this.ball2ModelAdapter;
	}


}