package hw04.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.Timer;

import hw04.model.paintStrategies.BallPaintStrategy;
import hw04.model.paintStrategies.IPaintStrategy;
import hw04.model.updateStrategies.*;
import provided.utils.dispatcher.IDispatcher;
import provided.utils.dispatcher.impl.SequentialDispatcher;
import provided.utils.displayModel.IATImage;
import provided.utils.displayModel.IDimension;
import provided.utils.loader.IObjectLoader;
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
	 * Ball to Model Adapter.
	 */
	private IBall2ModelAdapter _ball2ModelAdpt = new IBall2ModelAdapter() {

		@Override
		public IATImage getIATImage(Image image) {
			return IATImage.FACTORY.apply(image, _viewControlAdpt.getCanvas());
		}
	};

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
	 * Max ball radius.
	 */
	private int maxRadius = 50;

	/**
	 * Minimum ball radius
	 */
	private int minRadius = 20;

	/**
	 * Max ball starting speed
	 */
	private int maxSpeed = 20;

	/**
	 * Bounds for ball's initial velocity
	 */
	private Rectangle maxVelocity = new Rectangle(-maxSpeed, -maxSpeed, maxSpeed, maxSpeed);

	/**
	 * Dummy ball that holds the decoree strategies for the switcher strategies.
	 * A null ball-to-model adapter used initially but in start(), the null adapter is 
	 * replaced with the operational adapter.     
	 */
	private IBall switcherDummyBall = null;

	/**
	 * An algo to reset all the strategies to null/no-op strategies
	 */
	private IBallAlgo clearStrategiesAlgo = new IBallAlgo() {

		@Override
		public void caseDefault(IBall host) {
			host.setUpdateStrategy(IUpdateStrategy.NULL);
			host.setPaintStrategy(IPaintStrategy.NULL);
			//host.setInteractStrategy(IInteractStrategy.NULL);
		}
	};

	/**
	 * The algo used to install switcher strategies into a host ball.
	 */
	private IBallAlgo switcherInstallAlgo = new IBallAlgo() {

		@Override
		public void caseDefault(IBall host) {
			host.setUpdateStrategy(new IUpdateStrategy() {

				@Override
				public void updateState(IBall context, IDispatcher<IBallCmd> disp) {
					// delegate to the strategy in the dummy ball
					switcherDummyBall.getUpdateStrategy().updateState(context, disp);
				}

				@Override
				public void init(IBall context) {
					switcherDummyBall.getUpdateStrategy().init(context);
				}
			});
			host.setPaintStrategy(new IPaintStrategy() {

				@Override
				public void paint(Graphics g, IBall host) {
					// Delegate to the strategy in the dummy ball
					switcherDummyBall.getPaintStrategy().paint(g, host);
				}

				@Override
				public void init(IBall context) {
					switcherDummyBall.getPaintStrategy().init(context);
				}
			});
		}
	};

	/**
	 * Getter for the algorithm to install switcher strategies into a host ball
	 * @return the switcher installation algo
	 */
	public IBallAlgo getSwitcherInstallAlgo() {
		return this.switcherInstallAlgo;
	}

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
	 * Object loader for creating strategies
	 */
	private IObjectLoader<IUpdateStrategy> updateStrategy_loader = new ObjectLoaderPath<IUpdateStrategy>(
			(params) -> IUpdateStrategy.ERROR, "hw04.model.updateStrategies.");

	/**
	 * Object loader for paint strategies
	 */
	private IObjectLoader<IPaintStrategy> paintStrategy_loader = new ObjectLoaderPath<IPaintStrategy>(
			(params) -> IPaintStrategy.ERROR, "hw04.model.paintStrategies.");

	/**
	 * Load a ball into the system, where the given algo is used to configure the ball.
	 * @param ballAlgo An algorithm to configure the ball.
	 */
	public void loadBall(IBallAlgo ballAlgo) {
		Ball context = new Ball(
				rand.randomLoc(new Dimension(_viewControlAdpt.getCanvasDim().getWidth(),
						_viewControlAdpt.getCanvasDim().getHeight())),
				rand.randomInt(minRadius, maxRadius), rand.randomVel(maxVelocity), rand.randomColor(),
				_viewControlAdpt.getCanvasDim(), _ball2ModelAdpt, ballAlgo);
		myDispatcher.addObserver(context);
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
				//ball.paint(g);
				ball.getUpdateStrategy().updateState(ball, disp);
				ball.move();
				ball.getPaintStrategy().paint(g, ball);

			}
		});
		// The Graphics object is being given to all the sprites (Observers)
	}

	/**
	 * Take the strategy string from the drop list to create a IBall Algo
	 * @param className abbreviated name of the strategy
	 * @return an algorithm install the specified strategy
	 */
	public IBallAlgo makeUpdateStrategyFac(final String className) {

		if (null == className)
			return IBallAlgo.ERROR;
		return new IBallAlgo() {

			@Override
			public void caseDefault(IBall host) {
				// Create composite with existing strategy.  A named composite class is used here but an anonymous inner class would work too.
				// loadUpdateStrategy() expands the shortened name and uses an IObjectLoader to load it.
				host.setUpdateStrategy(new CompositeStrategy(host.getUpdateStrategy(),
						updateStrategy_loader.loadInstance(className + "Strategy")));
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
	 * Returns an IBallAlgo that can install an IPaintStrategy into its host as 
	 * specified by the given classname. 
	 * An error strategy is installed beeping error strategy if classname is null. 
	 * The toString() of the returned algo is the classname.
	 * 
	 * @param classname  Shortened name of desired strategy
	 * @return An algo to install the associated strategy
	 */
	public IBallAlgo makePaintStrategyFac(final String classname) {

		return new IBallAlgo() {

			@Override
			public void caseDefault(IBall host) {
				// Want generic composite paint strategy here, not MultiPaintStrategy which is specifically an Affine transform composite.
				host.setPaintStrategy(new IPaintStrategy() {
					IPaintStrategy paintStrat1 = host.getPaintStrategy(); // Save the host's current paint strategy
					IPaintStrategy paintStrat2 = paintStrategy_loader.loadInstance((classname + "PaintStrategy")); // Load the new paint strategy and save it.

					@Override
					public void paint(Graphics g, IBall host) {
						// Delegate to each composee
						paintStrat1.paint(g, host);
						paintStrat2.paint(g, host);
					}

					@Override
					public void init(IBall host) {
						// Delegate to each composee
						paintStrat1.init(host);
						paintStrat2.init(host);
					}

				});
			}

			/**
			 * Return the given class name string
			 */
			public String toString() {
				return classname;
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

		if (null == algo1 || null == algo2)
			return IBallAlgo.ERROR;

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
	 * Change the decoree strategies in the dummy ball using the given algorithm
	 * @param decoreeInstallAlgo the algorithm to install new decoree strategies into a ball
	 */
	public void switchSwitcherStrategy(IBallAlgo decoreeInstallAlgo) {
		switcherDummyBall.execute(clearStrategiesAlgo); // clear the installed strategies b/c the incoming algo will compose with the existing ones.
		switcherDummyBall.execute(decoreeInstallAlgo); // Install the new decoree strategies, which will be composed with the now null/no-op existing strategies in the dummy ball.
	}

	/**
	 * Start the ball model
	 */
	public void start() {
		_timer.start();

		switcherDummyBall = new Ball(null, 0, null, null, _viewControlAdpt.getCanvasDim(), _ball2ModelAdpt,
				new IBallAlgo() {

					@Override
					public void caseDefault(IBall host) {
						host.execute(clearStrategiesAlgo); // reset all the strategies to their null objects.
						host.setPaintStrategy(new BallPaintStrategy()); // default the painting to Ball at the beginning
						host.setUpdateStrategy(new StraightStrategy()); // default the painting to Ball at the beginning
					}
				});

	}

}