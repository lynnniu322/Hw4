package hw04.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import provided.utils.dispatcher.IDispatcher;
import provided.utils.displayModel.IDimension;

public interface IBall {

	/**
	 * Paint the ball on the given graphics
	 * @param g Graphics 
	 */
	void paint(Graphics g);

	/**
	 * Repaint and move the ball
	 */
	void update(IDispatcher<IBallCmd> disp, IBallCmd cmd);

	/**
	 * Update the balls
	 * @param disp the dispatcher for the balls
	 */
	void updateState(IDispatcher<IBallCmd> disp);

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
	int getDiameter();

	/**
	 * Resize the ball
	 * @param diameter diameter of ball
	 */
	void setDiameter(int diameter);

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
	 * Get the strategy
	 * @return strategy strategy of ball
	 */
	IUpdateStrategy getStrategy();

	/**
	 * Set the strategy
	 * @param strategy strategy of ball
	 */
	void setStrategy(IUpdateStrategy strategy);

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

}