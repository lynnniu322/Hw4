package hw04.model.strategies;

import java.awt.Graphics;
import java.awt.Point;

import hw04.model.Ball;
import hw04.model.IBall;
import hw04.model.IBallAlgo;
import hw04.model.IBallCmd;
import hw04.model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;

/**
 * The ball shrinks as it bounces off the walls
 *
 */
public class ShrinkingStrategy implements IUpdateStrategy {

	@Override
	public void updateState(IBall ball, IDispatcher<IBallCmd> dip){
		if (ball.getBounced()) {
			if (ball.getDiameter() > 0) {
				ball.setDiameter(ball.getDiameter() - 2);
				// shift location to maintain center point
				ball.setLoc(new Point(ball.getLoc().x + 1, ball.getLoc().y + 1));
			}
		}

	}

}
