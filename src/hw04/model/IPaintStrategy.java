package hw04.model;

import java.awt.Graphics;

import provided.utils.dispatcher.IDispatcher;

public interface IPaintStrategy {

	void paint(Graphics g, IBall host);

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
