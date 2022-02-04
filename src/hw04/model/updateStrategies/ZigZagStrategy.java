package hw04.model.updateStrategies;

import java.awt.Point;

import hw04.model.IBall;
import hw04.model.IBallCmd;
import provided.utils.dispatcher.IDispatcher;

/**
 * The strategy that makes the ball runs in zig zag path
 *
 */
public class ZigZagStrategy implements IUpdateStrategy {

	@Override
	public void updateState(IBall ball, IDispatcher<IBallCmd> dip) {
		// TODO Auto-generated method stub
		Point currVel = ball.getVelocity();
		Point newVel = new Point(-currVel.x, currVel.y); // Change the direction of the velocity
		ball.setVelocity(newVel);
	}

	@Override
	public void init(IBall ball) {
		// TODO Auto-generated method stub

	}

}