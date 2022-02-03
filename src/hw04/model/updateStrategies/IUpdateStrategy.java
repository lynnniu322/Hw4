package hw04.model.updateStrategies;


import hw04.model.IBall;
import hw04.model.IBallCmd;
import provided.utils.dispatcher.IDispatcher;

/**
 * The interface of the strategy
 *
 */
public interface IUpdateStrategy {

	/**
	 * Update the state of the ball in ways not encompassed by move or paint
	 * @param ball the ball object
	 * @param disp the dispatcher
	 */
	public abstract void updateState(IBall ball, IDispatcher<IBallCmd> disp);

	/**
	 * Update the state of the ball in ways not encompassed by move or paint
	 * @param ball the ball object
	 */
	public abstract void init(IBall ball);

	/**
	 * No-opt for null strategy
	 */
	public static final IUpdateStrategy NULL = new IUpdateStrategy() {
		@Override
		public void updateState(IBall ball, IDispatcher<IBallCmd> disp) {
		}

		@Override
		public void init(IBall ball) {
		}

	};

	/**
	 * The error strategy
	 */
	public static final IUpdateStrategy ERROR = new IUpdateStrategy() {

		@Override
		public void updateState(IBall ball, IDispatcher<IBallCmd> disp) {			
		}

		@Override
		public void init(IBall ball) {
		}
		
	};
}