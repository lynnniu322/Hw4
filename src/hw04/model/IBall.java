package hw04.model;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;

import hw04.model.paintStrategies.IPaintStrategy;
import hw04.model.updateStrategies.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;
import provided.utils.displayModel.IDimension;

/**
 * Interface for Ball.
 * @author yihan
 *
 */
public interface IBall {

	/**
	 * Paint the ball on the given graphics
	 * @param g Graphics 
	 */
	void paint(Graphics g);

	/**
	 * Repaint and move the ball
	 * @param disp dispatcher with the balls as observers
	 * @param cmd the command executed by the balls
	 */
	void update(IDispatcher<IBallCmd> disp, IBallCmd cmd);


	/**
	 * Get the location of the ball
	 * @return current location of the ball
	 */
	Point getLoc();

	/**
	 * Set the location of the ball
	 * @param loc updated location
	 */
	void setLoc(Point loc);

	/**
	 * Get the ball color
	 * @return color color of ball
	 */
	Color getColor();

	/**
	 * Color the ball
	 * @param color color of ball
	 */
	void setColor(Color color);

	/**
	 * Get the diameter
	 * @return diameter diameter of ball
	 */
	int getRadius();

	/**
	 * Resize the ball
	 * @param diameter diameter of ball
	 */
	void setRadius(int diameter);

	/**
	 * Get the velocity
	 * @return velocity velocity of ball
	 */
	Point getVelocity();

	/**
	 * Change the ball velocity
	 * @param velocity velocity of ball
	 */
	void setVelocity(Point velocity);

	/**
	 * move the ball, bouncing if at the edge of the screen
	 */
	void move();

	/**
	 * @return the dimension of the ball
	 */
	IDimension getDimension();

	/**
	 * @return the boolean indicating if the ball touches the walls
	 */
	Boolean getBounced();
	
	/**
	 * Execute the given algorithm
	 * @param iUpdateStrategy The algorithm to execute
	 */
	public void execute(IBallAlgo iUpdateStrategy);

	/**
	 * Get the strategy
	 * @param algo algo
	 */
	void setAlgo(IBallAlgo algo);
	
	/**
	 * Get the strategy
	 * @param iUpdateStrategy algo
	 */
	void setUpdateStrategy(IUpdateStrategy iUpdateStrategy);

	/**
	 * Get the strategy
	 * @return strategy strategy of ball
	 */
	IUpdateStrategy getUpdateStrategy();

	/**
	 * Get the strategy
	 * @return strategy strategy of ball
	 */
	IBallAlgo getAlgo();

	/**
	 * Set the paint strategy.
	 * @param iPaintStrategy the paint strategy implemented by the ball
	 */
	void setPaintStrategy(IPaintStrategy iPaintStrategy);

	/**
	 * Get the paint strategy.
	 * @return IPaintStrategy 
	 */
	IPaintStrategy getPaintStrategy();
	
	/**
	 * @return ViewControlAdapter
	 */
	IViewControlAdapter getViewControlAdapter();
	
	/**
	 * 
	 * @return painting canvas
	 */
	Container getCanvas();

}