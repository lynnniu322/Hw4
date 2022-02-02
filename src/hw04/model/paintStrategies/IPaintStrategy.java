package hw04.model.paintStrategies;

import java.awt.Graphics;

import hw04.model.IBall;

/**
 * Top level interface that defines what a painting strategy can do.
 * @author yihan
 *
 */
public interface IPaintStrategy {

	/**
	 * Paint the IBall.
	 * @param g
	 * @param host
	 */
	void paint(Graphics g, IBall host);

	/**
	 * Initialize the strategy and the host ball.
	 * @param host
	 */
	void init(IBall host);
	
	/**
	 * No-opt for null strategy
	 */
	public static final IPaintStrategy NULL = new IPaintStrategy() {
		@Override
		public void paint(Graphics g, IBall host) {
		}
		
		@Override
		public void init(IBall host) {
		}


	};
	
	/**
	 * The error strategy
	 */
	public static final IPaintStrategy ERROR = new IPaintStrategy() {

		@Override
		public void paint(Graphics g, IBall host) {
		}
		
		@Override
		public void init(IBall host) {
		}
		
	};

}
