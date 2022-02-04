package hw04.model.updateStrategies;

import hw04.model.IBall;
import hw04.model.IBallCmd;
import provided.utils.dispatcher.IDispatcher;

/**
 * @author cindy
 * The composite strategy that is composed of two other IUpdateStrategies
 *
 */
public class CompositeStrategy implements IUpdateStrategy {

	/**
	 * Strategy 1 of the composite strategy.
	 */
	private IUpdateStrategy strategy1;

	/**
	 * Strategy 2 of the composite strategy.
	 */
	private IUpdateStrategy strategy2;

	/**
	 * Constructor of the combined strategy.
	 * @param strategy1 the first strategy to be merged
	 * @param strategy2 the second strategy to be merged
	 */
	public CompositeStrategy(IUpdateStrategy strategy1, IUpdateStrategy strategy2) {
		this.strategy1 = strategy1;
		this.strategy2 = strategy2;
	}

	@Override
	public void updateState(IBall context, IDispatcher<IBallCmd> disp) {
		this.strategy1.updateState(context, disp);
		this.strategy2.updateState(context, disp);

	}

	@Override
	public void init(IBall ball) {
		// TODO Auto-generated method stub

	}

}
