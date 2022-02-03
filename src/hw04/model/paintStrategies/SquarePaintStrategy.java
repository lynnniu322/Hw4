package hw04.model.paintStrategies;

import java.awt.Graphics;

import hw04.model.IBall;

/**
 * Simple square paint strategy without affine transformation.
 * @author yihan
 *
 */
public class SquarePaintStrategy implements IPaintStrategy {


	/**
	 * Constructor of square paint strategy.
	 */
	public SquarePaintStrategy() {
		   
	}


	@Override
	public void init(IBall host) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics g, IBall host) {
		int halfSide = host.getRadius();
	    g.setColor(host.getColor());
	    g.fillRect(host.getLoc().x-halfSide, host.getLoc().y-halfSide, 2*halfSide, 2*halfSide);
		
	}

}
