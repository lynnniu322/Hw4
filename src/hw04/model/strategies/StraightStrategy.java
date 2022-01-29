package hw04.model.strategies;

import java.awt.Graphics;

import hw04.model.Ball;
import hw04.model.IBall;
import hw04.model.IBallCmd;
import hw04.model.IBallAlgo;
import provided.utils.dispatcher.IDispatcher;

/**
 * The ball bounces off the walls
 *
 */
public class StraightStrategy implements IBallAlgo {

	@Override
	public void caseDefault(IBall ball) {
	}

}

