package hw04.model.strategies;

import java.awt.Point;

import hw04.model.Ball;
import hw04.model.IBall;
import hw04.model.IBallCmd;
import hw04.model.IBallAlgo;
import provided.utils.dispatcher.IDispatcher;

/**
 * The strategy that makes the ball runs in zig zag path
 *
 */
public class ZigZagStrategy implements IBallAlgo {

	@Override
	public void caseDefault(IBall ball) {
		// TODO Auto-generated method stub
		Point currVel = ball.getVelocity();
		Point newVel = new Point(-currVel.x, currVel.y); // Change the direction of the velocity
		ball.setVelocity(newVel);
	}


}