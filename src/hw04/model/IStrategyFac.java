package hw04.model;

import java.awt.Graphics;

import provided.utils.dispatcher.IDispatcher;

/**
 * The interface that makes specific IUpdateStrategy
 *
 */
public interface IStrategyFac {

	/**
	* Instantiate the specific IUpdateStrategy for which this factory is defined
	* @return An IUpdateStrategy instance.
	*/
	public IUpdateStrategy make();

	/**
	 * 
	 */
	public static final IStrategyFac NULL = new IStrategyFac() {
		@Override
		public IUpdateStrategy make() {
			return null;
		}

	};

	/**
	 * A factory for a beeping error strategy that beeps the speaker every 25 updates.
	 * Either use the _errorStrategyFac variable directly if you need a factory that makes an error strategy,
	 * or call _errorStrategyFac.make() to create an instance of a beeping error strategy.
	 */
	public static final IStrategyFac ERROR = new IStrategyFac() {
		@Override
		/**
		 * Make the beeping error strategy
		 * @return  An instance of a beeping error strategy
		 */
		public IUpdateStrategy make() {
			return new IUpdateStrategy() {
				private int count = 0; // update counter

				@Override
				/**
				 * Beep the speaker every 25 updates
				 */
				public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
					if (25 < count++) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						count = 0;
					}
				}
			};
		}
	};

}
