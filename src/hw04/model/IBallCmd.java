package hw04.model;

import provided.utils.dispatcher.IDispatcher;

/**
 * Interface that represents commands sent through the dispatcher to process the balls
 */
@FunctionalInterface
public abstract interface IBallCmd {
	/**
	     * The method run by the ball's update method which is called when the ball is updated by the dispatcher.
	     * @param context The ball that is calling this method.   The context under which the command is to be run.
	     * @param disp The Dispatcher that sent the command out.
	     */
	    public abstract void apply(IBall context, IDispatcher<IBallCmd> disp);


}
