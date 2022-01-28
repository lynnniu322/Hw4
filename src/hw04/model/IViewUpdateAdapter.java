package hw04.model;

import hw04.model.IViewUpdateAdapter;

/**
 * Interface that goes from the model to the view that enables the model conduct update tasks to view
 */
public interface IViewUpdateAdapter {

	/**
	 * The method that tells the view to update
	 */
	public void update();

	/**
	 * No-op "null" adapter
	 */
	public static final IViewUpdateAdapter NULL_OBJECT = new IViewUpdateAdapter() {
		public void update() {
		}
	};
}