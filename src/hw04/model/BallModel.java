package hw04.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.Timer;

import hw04.model.strategies.*;
import provided.utils.dispatcher.IDispatcher;
import provided.utils.dispatcher.IObserver;
import provided.utils.dispatcher.impl.SequentialDispatcher;
import provided.utils.displayModel.IDimension;
import provided.utils.loader.IObjectLoader;
import provided.utils.loader.impl.ObjectLoader;
import provided.utils.loader.impl.ObjectLoaderPath;
import provided.utils.valueGenerator.IRandomizer;
import provided.utils.valueGenerator.impl.Randomizer;

/**
 * The Model class
 */
public class BallModel {
	
	// TODO: Add private instance of switcher here

	/**
	 * Dispatcher for spawned balls
	 */
	private IDispatcher<IBallCmd> myDispatcher = new SequentialDispatcher<IBallCmd>();

	/**
	 * Adapter to the view
	 */
	private IViewControlAdapter _viewControlAdpt = IViewControlAdapter.NULL_OBJECT; // Insures that the adapter is always valid
	/**
	 * 
	 */
	private IViewUpdateAdapter _viewUpdateAdpt = IViewUpdateAdapter.NULL_OBJECT; // Insures that the adapter is always valid


	/**
	 * Dimensions of the canvas upon which the balls will be painted
	 */
	IDimension ballCanvasDim = _viewControlAdpt.getCanvasDim(); // ballCanvasDim is used by the balls to determine their bouncing behavior.

	/**
	 * Randomizer for ball parameters
	 */
	private IRandomizer rand = Randomizer.Singleton;

	/**
	 * Length of a time slice
	 */
	private int _timeSlice = 50; // update every 50 milliseconds

	/**
	 * Timer for update ticks
	 */
	private Timer _timer = new Timer(_timeSlice, (e) -> _viewUpdateAdpt.update());

	/**
	 * Max ball diameter
	 */
	private int maxDiameter = 30;
	
	/**
	 * Minimum ball diameter
	 */
	private int minDiameter = 5;

	/**
	 * Max ball starting speed
	 */
	private int maxSpeed = 20;

	/**
	 * Bounds for ball's initial velocity
	 */
	private Rectangle maxVelocity = new Rectangle(-maxSpeed, -maxSpeed, maxSpeed, maxSpeed);

	/**
	 * The constructor for BallModel 
	 * @param viewControlAdpt the adapter from model to view for control tasks
	 * @param viewUpdateAdpt the adapter from view to model for update tasks
	 */
	public BallModel(IViewControlAdapter viewControlAdpt, IViewUpdateAdapter viewUpdateAdpt) {
		this._viewControlAdpt = viewControlAdpt;
		this._viewUpdateAdpt = viewUpdateAdpt;
	}

	/**
	 * Start the ball model
	 */
	public void start() {
		_timer.start();
	}
	
	/**
	 * The strategy implemented by the switcher balls
	 */
	//private SwitcherStrategy switcher = new SwitcherStrategy(new StraightStrategy());
	
	/**
	 * Object loader for creating strategies
	 */
	private IObjectLoader<IUpdateStrategy> strategy_loader = new ObjectLoaderPath<IUpdateStrategy>((params) -> IUpdateStrategy.ERROR, "hw04.model.strategies.");

	
	/**
	 * Load a ball into the system, where the given algo is used to configure the ball.
	 * @param ballAlgo An algorithm to configure the ball.
	 */
	public void loadBall(IBallAlgo ballAlgo) {
		myDispatcher.addObserver(new Ball(
				rand.randomLoc(new Dimension(_viewControlAdpt.getCanvasDim().getWidth(), _viewControlAdpt.getCanvasDim().getHeight())),
				rand.randomInt(minDiameter, maxDiameter), rand.randomVel(maxVelocity), rand.randomColor(),
				_viewControlAdpt.getCanvasDim(), ballAlgo));
	}
	
	/**
	 * Clear all balls
	 */
	public void clearBall() {
		this.myDispatcher.removeAllObservers();
	}

	/**
	 * This is the method that is called by the view's adapter to the model, i.e. is called by IView2ModelAdapter.paint().
	 * This method will update the sprites's painted locations by painting all the sprites
	 * onto the given Graphics object.
	 * @param g The Graphics object from the view's paintComponent() call.
	 */
	public void paint(Graphics g) {
		myDispatcher.updateAll(new IBallCmd() {
			    
			    /**
			     * Do stuff with the ball
			     */
			@Override
			    public void apply(IBall ball, IDispatcher<IBallCmd> disp) {
			    	ball.paint(g);
			        ball.move();
			        ball.execute(ball.getAlgo());
			    }          
			});
			// The Graphics object is being given to all the sprites (Observers)
	}
	
	/**
	 * Take the strategy string from the drop list to create a IBall Algo
	 * @param className abbreviated name of the strategy
	 * @return A factory to make that strategy
	 */
	public IBallAlgo makeAlgo(final String className) {
		
			if (null == className) return IBallAlgo.ERROR;
		    return new IBallAlgo() {
		        /**
		         * Instantiate a strategy corresponding to the given class name.
		         * @return An IUpdateStrategy instance
		         */
		        @Override
				public void caseDefault(IBall host) {
					// Create composite with existing strategy.  A named composite class is used here but an anonymous inner class would work too.
					// loadUpdateStrategy() expands the shortened name and uses an IObjectLoader to load it.
					host.setUpdateStrategy(strategy_loader.loadInstance(className+"Strategy"));
					host.setUpdateStrategy(new CompositeStrategy(host.getUpdateStrategy(), strategy_loader.loadInstance(className+"Strategy")));
					host.getUpdateStrategy().updateState(host, myDispatcher);
				}
		        /**
		         * Return the given class name string
		         */
		        public String toString() {
		            return className;
		        }
	
		    };
	}
	
	/**
	 * Returns an IStrategyFac that can instantiate a MultiStrategy with the two
	 * strategies made by the two given IStrategyFac objects. Returns null if
	 * either supplied factory is null. The toString() of the returned factory
	 * is the toString()'s of the two given factories, concatenated with "-".
	 * If either factory is null, then a factory for a beeping error strategy is returned.
	 *
	 * @param algo1 An IStrategyFac for a strategy
	 * @param algo2 An IStrategyFac for a strategy
	 * @return An IStrategyFac for the composition of the two strategies
	 */
	public IBallAlgo combineAlgos(IBallAlgo algo1, IBallAlgo algo2) {
		
		if (null == algo1 || null == algo2) return IBallAlgo.ERROR;
    	return new IBallAlgo() {
    		
    		@Override
			public void caseDefault(IBall host) {
				// Always delegate to the host to enable type-dependent processing of the algorithm
				host.execute(algo1);
				host.execute(algo2);
			}
            

        /**
         * Return a string that is the toString()'s of the given strategy factories concatenated with a "-"
         */
        public String toString() {
            return algo1.toString() + "-" + algo2.toString();
        }
    };
	};
	
	/**
	 * Return the switcher
	 * @return the switcher
	 */
	//public SwitcherStrategy getSwitcherStrategy() {
	//	return this.switcher;
	//}
	
	/**
	 * Switch the strategy of the switcher balls
	 * @param strategy the strategy of the switcher balls
	 */
	//public void switchSwitcherStrategy(IUpdateStrategy strategy) {
	//	this.switcher.setStrategy(strategy);
	//}


}