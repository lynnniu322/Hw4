package hw04.model.strategies;

import java.awt.Dimension;
import java.awt.Graphics;

import hw04.model.Ball;
import hw04.model.IBallCmd;
import hw04.model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;
import provided.utils.valueGenerator.IRandomizer;
import provided.utils.valueGenerator.impl.Randomizer;

/**
 * The strategy that makes the ball changes location
 *
 */
public class TeleportStrategy implements IUpdateStrategy {

	/**
	 * Randomizer for ball parameters
	 */
	private IRandomizer rand = Randomizer.Singleton;

	/**
	 * The counter
	 */
	private int counter = 0;

	@Override
	public void updateState(Ball ball, IDispatcher<IBallCmd> disp) {
		if (counter == 15) {
			ball.setLoc(rand.randomLoc(new Dimension(ball.getDimension().getWidth(), ball.getDimension().getHeight())));
			counter = 0;
		} else {
			counter += 1;
		}
	}

}