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
	private SwitcherStrategy switcher = new SwitcherStrategy(new StraightStrategy());
	
	/**
	 * Object loader for creating strategies
	 */
	private IObjectLoader<IUpdateStrategy> strategy_loader = new ObjectLoaderPath<IUpdateStrategy>((params) -> IUpdateStrategy.ERROR, "hw04.model.strategies.");

	/**
	 * Object loader for creating balls
	 */
	private IObjectLoader<IObserver<IBallCmd>> ballLoader = new ObjectLoader<>(
			args -> new Ball(new Point(0,0), 0, new Point(0,0), Color.BLACK, _viewControlAdpt.getCanvasDim(),new StraightStrategy()));
	/**
	 * Spawn in a new ball
	 * @param strategy the ball's behavior strategy
	 */
	public void loadBall(IUpdateStrategy strategy) {	
		
		this.myDispatcher.addObserver(ballLoader.loadInstance("hw04.model.Ball", 
			rand.randomLoc(new Dimension(_viewControlAdpt.getCanvasDim().getWidth(),
			_viewControlAdpt.getCanvasDim().getHeight())), // place in a random location
			rand.randomInt(minDiameter, maxDiameter), // give random diameter
			rand.randomVel(maxVelocity), // give random initial velocity
			rand.randomColor(), // give random color
			_viewControlAdpt.getCanvasDim(),
			strategy));
		
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
			    public void apply(Ball ball, IDispatcher<IBallCmd> disp) {
			    	ball.paint(g);
			        ball.move();
			        ball.updateState(disp);
			    }          
			});
			// The Graphics object is being given to all the sprites (Observers)
	}
	
	/**
	 * Take the strategy string from the drop list to create a strategy factory
	 * @param className abbreviated name of the strategy
	 * @return A factory to make that strategy
	 */
	public IStrategyFac makeStrategyFac(final String className) {
		
			if (null == className) return IStrategyFac.ERROR;
		    return new IStrategyFac() {
		        /**
		         * Instantiate a strategy corresponding to the given class name.
		         * @return An IUpdateStrategy instance
		         */
		        public IUpdateStrategy make() {
		        	//dynamically load a strategy instance given its fully qualified class name
		            return strategy_loader.loadInstance(className+"Strategy"); //loadStrategy(fixName(className));
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
	 * @param factory1 An IStrategyFac for a strategy
	 * @param factory2 An IStrategyFac for a strategy
	 * @return An IStrategyFac for the composition of the two strategies
	 */
	public IStrategyFac combineStrategyFacs(IStrategyFac factory1, IStrategyFac factory2) {
		
		if (null == factory1 || null == factory2) return IStrategyFac.ERROR;
    	return new IStrategyFac() {
        /**
         * Instantiate a new strategy by combining the given strategies
         */
        public IUpdateStrategy make() {
            return new IUpdateStrategy() {
				IUpdateStrategy strat1 = factory1.make();
				IUpdateStrategy strat2 = factory2.make();

				@Override
				public void updateState(Ball ball, IDispatcher<IBallCmd> disp){
					strat1.updateState(ball, disp);
					strat2.updateState(ball, disp);
				}

        	};
		}

        /**
         * Return a string that is the toString()'s of the given strategy factories concatenated with a "-"
         */
        public String toString() {
            return factory1.toString() + "-" + factory2.toString();
        }
    };
	};
	
	/**
	 * Return the switcher
	 * @return the switcher
	 */
	public SwitcherStrategy getSwitcherStrategy() {
		return this.switcher;
	}
	
	/**
	 * Switch the strategy of the switcher balls
	 * @param strategy the strategy of the switcher balls
	 */
	public void switchSwitcherStrategy(IUpdateStrategy strategy) {
		this.switcher.setStrategy(strategy);
	}


}
