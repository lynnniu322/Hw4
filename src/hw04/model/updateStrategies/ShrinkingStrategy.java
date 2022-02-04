package hw04.model.updateStrategies;

import java.awt.Point;

import hw04.model.IBall;
import hw04.model.IBallCmd;
import provided.utils.dispatcher.IDispatcher;

/**
 * The ball shrinks as it bounces off the walls
 *
 */
public class ShrinkingStrategy implements IUpdateStrategy {

	@Override
	public void updateState(IBall ball, IDispatcher<IBallCmd> dip){
		if (ball.getBounced()) {
			if (ball.getRadius() > 0) {
				ball.setRadius(ball.getRadius() - 1);
				// shift location to maintain center point
				ball.setLoc(new Point(ball.getLoc().x + 1, ball.getLoc().y + 1));
			}
		}

	}

	@Override
	public void init(IBall ball) {
		// TODO Auto-generated method stub
		
	}

}
