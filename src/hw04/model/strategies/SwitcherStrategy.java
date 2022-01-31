package hw04.model.strategies;

import java.awt.Graphics;

import hw04.model.Ball;
import hw04.model.IBall;
import hw04.model.IBallCmd;
import hw04.model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;

/**
 * The strategy of the switcher balls
 *
 */
public class SwitcherStrategy implements IUpdateStrategy {

	/**
	 * The strategy of the switcher balls
	 */
	private IUpdateStrategy strategy;

	/**
	 * Constructor for the SwitcherStrategy
	 * @param strategy the strategy for the switcher balls to implement
	 */
	public SwitcherStrategy(IUpdateStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public void updateState(IBall ball) {
		// TODO Auto-generated method stub
		this.strategy.updateState(ball);
	}

	/**
	 * @param newStrategy the strategy for the switcher balls to implement
	 */
	public void setStrategy(IUpdateStrategy newStrategy) {
		this.strategy = newStrategy;
	}
}