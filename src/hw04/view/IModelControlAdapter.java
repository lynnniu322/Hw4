package hw04.view;

/**
 * Adapter that the view uses to communicate to the model for non-repetitive control tasks such as manipulating strategies.
 * @param <TDropListItem> The type of objects put into the view's drop lists.
 *
 */
public interface IModelControlAdapter<TDropListItem> {

	/**
	 * Take the given short strategy name and return a corresponding something to put onto both drop lists.
	 * @param className The shortened class name of the desired strategy
	 * @return Something to put onto both the drop lists.
	 */
	public TDropListItem addStrategy(String className);

	/**
	 * Call the model's method clearBall()
	 */
	public void clearBall();

	/**
	 * Call the model's method loadBall()
	 * @param t the item in the drop list
	 */
	public void makeBall(TDropListItem t);

	/**
	 * Call the model's method to make switcher balls
	 */
	public void makeSwitcherBall();

	/**
	 * The method combine the two items in the drop list
	 * @param item1 the first item in the drop list
	 * @param item2 the second item in the drop list
	 * @return the combined item of the two items in the drop list
	 */
	public TDropListItem combineStrategies(TDropListItem item1, TDropListItem item2);

	/**
	 * The method switch the strategy of Switcher Strategy to the item in the drop list
	 * @param item the item in the drop list
	 */
	public void switchStrategy(TDropListItem item);

}
