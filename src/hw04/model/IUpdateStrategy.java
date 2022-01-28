package hw04.model;


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
	public abstract void updateState(Ball ball, IDispatcher<IBallCmd> disp);

	/**
	 * No-opt for null strategy
	 */
	public static final IUpdateStrategy NULL = new IUpdateStrategy() {
		@Override
		public void updateState(Ball ball, IDispatcher<IBallCmd> disp) {
		}

	};

	/**
	 * The error strategy
	 */
	public static final IUpdateStrategy ERROR = new IUpdateStrategy() {
		private int count = 100;

		@Override
		public void updateState(Ball ball, IDispatcher<IBallCmd> disp) {
			if (this.count >= 0) {
				if (this.count == 0) {
					disp.removeObserver(ball);
				} else {
					count--;
				}
			}
		}
	};
}
