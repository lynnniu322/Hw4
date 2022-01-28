package hw04.view;

import java.awt.Graphics;

import hw04.view.IModelUpdateAdapter;

/**
 * Adapter that the view uses to communicate to the model for update tasks.
 * 
 */
public interface IModelUpdateAdapter {

	/**
	 * Call the Model's method paint(g)
	 * @param g the Graphics element
	 */
	public void paint(Graphics g);

	/**
	 * No-op "null" adapter 
	 */
	public static final IModelUpdateAdapter NULL_OBJECT = new IModelUpdateAdapter() {

		public void paint(Graphics g) {
		}
	};

}