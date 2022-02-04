package hw04.model.updateStrategies;

import java.awt.Point;

import hw04.model.IBall;
import hw04.model.IBallCmd;
import provided.utils.dispatcher.IDispatcher;

/**
 * The balls slows down as it bounces off the walls
 *
 */
public class SlowingStrategy implements IUpdateStrategy {

	@Override
	public void updateState(IBall ball, IDispatcher<IBallCmd> disp) {
		if (ball.getBounced() == true)
			ball.setVelocity(new Point((int) (ball.getVelocity().x * 0.9), (int) (ball.getVelocity().y * 0.9)));
	}

	@Override
	public void init(IBall ball) {
		// TODO Auto-generated method stub
		
	}

}
