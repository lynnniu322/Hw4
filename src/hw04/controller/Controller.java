package hw04.controller;

import java.awt.EventQueue;
import java.awt.Graphics;

import hw04.view.BallGUI;
import hw04.view.IModelControlAdapter;
import hw04.view.IModelUpdateAdapter;
import provided.utils.displayModel.IDimension;
import hw04.model.BallModel;
import hw04.model.IStrategyFac;
import hw04.model.IViewControlAdapter;
import hw04.model.IViewUpdateAdapter;

/**						
* MVC Controller for the system
*/

public class Controller {

	// Fields for the adapters to close over:
	/**
	 * model
	 */
	private BallModel model; // starts off null but will be fine when the constructor is finished.
	/**
	 * view
	 */
	private BallGUI<IStrategyFac> view; // starts off null but will be fine when the constructor is finished.

	/**
	 * Controller constructor builds the system
	 */
	public Controller() {
		// Here the model is shown being constructed first then the view but it could easily be the other way around if needs dictated it.
		// set the model field
		view = new BallGUI<IStrategyFac>(new IModelControlAdapter<IStrategyFac>() {

			@Override
			public IStrategyFac addStrategy(String className) {
				return model.makeStrategyFac(className);
			}

			@Override
			public void makeBall(IStrategyFac item) {
				if (item != null) {
					model.loadBall(item.make());
				}
			}

			@Override
			public void clearBall() {
				model.clearBall();

			}

			@Override
			public void makeSwitcherBall() {
				model.loadBall(model.getSwitcherStrategy());

			}

			@Override
			public IStrategyFac combineStrategies(IStrategyFac item1, IStrategyFac item2) {
				return model.combineStrategyFacs(item1, item2);
			}

			@Override
			public void switchStrategy(IStrategyFac item) {
				model.switchSwitcherStrategy(item.make());

			}

		}, new IModelUpdateAdapter() {
			@Override
			public void paint(Graphics g) {
				model.paint(g);
			}
		});

		model = new BallModel(new IViewControlAdapter() {

			@Override
			public IDimension getCanvasDim() {
				return new IDimension() {
					public int getWidth() {
						return view.getCanvas().getWidth();
					}

					public int getHeight() {
						return view.getCanvas().getHeight();
					}
				};
			}
		}, new IViewUpdateAdapter() {
			@Override
			public void update() {
				view.update();
			}
		});
		// At this point, both the model and view are instantiated as well as both adapters and both adapters reference non-null model and view fields.
	}

	/**
	 * Start the system
	*/
	public void start() {
		model.start(); // It is usually better to start the model first but not always.
		view.start();
	}

	/**
	 * Launch the application.
	 * @param args Arguments given by the system or command line.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { // Java specs say that the system must be constructed on the GUI event thread
			public void run() {
				try {
					Controller controller = new Controller(); // instantiate the system
					controller.start(); // start the system
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}