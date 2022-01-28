package hw04.model.strategies;

import java.awt.Graphics;
import java.awt.Point;

import hw04.model.Ball;
import hw04.model.IBallCmd;
import hw04.model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;

/**
 * The balls slows down as it bounces off the walls
 *
 */
public class SlowingStrategy implements IUpdateStrategy {

	@Override
	public void updateState(Ball ball, IDispatcher<IBallCmd> disp) {
		if (ball.getBounced() == true)
			ball.setVelocity(new Point((int) (ball.getVelocity().x * 0.9), (int) (ball.getVelocity().y * 0.9)));
	}

}
