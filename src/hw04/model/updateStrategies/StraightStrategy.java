package hw04.model.updateStrategies;

import hw04.model.IBall;
import hw04.model.IBallCmd;
import provided.utils.dispatcher.IDispatcher;

/**
 * The ball bounces off the walls
 *
 */
public class StraightStrategy implements IUpdateStrategy {

	@Override
	public void updateState(IBall ball, IDispatcher<IBallCmd> dip) {
	}

	@Override
	public void init(IBall ball) {
		// TODO Auto-generated method stub

	}

}
