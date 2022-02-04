package hw04.model.updateStrategies;

import hw04.model.IBall;
import hw04.model.IBallCmd;
import provided.utils.dispatcher.IDispatcher;
import provided.utils.valueGenerator.IRandomizer;
import provided.utils.valueGenerator.impl.Randomizer;

/**
 * The ball changes color as it bounces off the walls
 *
 */
public class BounceColorStrategy implements IUpdateStrategy {

	/**
	 * Randomizer for ball parameters
	 */
	private IRandomizer rand = Randomizer.Singleton;

	/**
	 * Changes color if the ball bounced this tick
	 */
	@Override
	public void updateState(IBall ball, IDispatcher<IBallCmd> dip) {
		if (ball.getBounced() == true) {
			ball.setColor(rand.randomColor());

		}
	}

	@Override
	public void init(IBall ball) {
		// TODO Auto-generated method stub

	}

}