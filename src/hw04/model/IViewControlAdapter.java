package hw04.model;

import java.awt.Container;
import hw04.model.IViewControlAdapter;
import provided.utils.displayModel.IDimension;

/**
 * Interface that goes from the model to the view that enables the model to talk to the view
 */
public interface IViewControlAdapter {

	/**
	 * Call the view's method to get the dimension of canvas
	 * @return The dimensions of the view canvas
	 */
	public IDimension getCanvasDim();

	/**
	 *
	 * @return the painting canvas
	 */
	public abstract Container getCanvas();

	/**
	 * No-op "null" adapter
	 */
	public static final IViewControlAdapter NULL_OBJECT = new IViewControlAdapter() {

		public IDimension getCanvasDim() {
			return null;
		}

		@Override
		public Container getCanvas() {
			return null;
		}

	};
}
