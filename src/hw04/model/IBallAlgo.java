package hw04.model;

import provided.utils.dispatcher.IDispatcher;

/**
 * An algorithm to process a host ball
 *
 */
public interface IBallAlgo {
	
	
	
	/**
	 * The default case process
	 * @param host The host ball to process.
	 */
	public default void caseDefault(IBall host) {
		host.getUpdateStrategy().updateState(host, disp);
	};
	
	/**
	 * The error strategy
	 */
	public static final IBallAlgo ERROR = new IBallAlgo() {

		@Override
		public void caseDefault(IBall host) {
			// TODO Auto-generated method stub
			
		}
};
}
