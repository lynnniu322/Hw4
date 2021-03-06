package hw04.model.updateStrategies;

import hw04.model.IBall;
import hw04.model.IBallCmd;
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
	public void updateState(IBall ball, IDispatcher<IBallCmd> disp) {
		// TODO Auto-generated method stub
		this.strategy.updateState(ball, disp);
	}

	/**
	 * @param newStrategy the strategy for the switcher balls to implement
	 */
	public void setStrategy(IUpdateStrategy newStrategy) {
		this.strategy = newStrategy;
	}

	@Override
	public void init(IBall ball) {
		// TODO Auto-generated method stub

	}
}