package hw04.model;

import java.awt.Image;

import provided.utils.displayModel.IATImage;

/**
 * Interface that goes from ball to model.
 * @author yihan
 *
 */
public interface IBall2ModelAdapter {

	/**
	 * @param image the image to be painted
	 * @return IATImage
	 */
	public IATImage getIATImage(Image image);

	//	/**
	//	 * No-op "null" adapter
	//	 */
	//	public static final IBall2ModelAdapter NULL_OBJECT = new IBall2ModelAdapter() {
	//
	//
	//		@Override
	//		public IATImage getIATImage(Image image) {
	//			return null;
	//		}
	//
	//
	//	};
}
