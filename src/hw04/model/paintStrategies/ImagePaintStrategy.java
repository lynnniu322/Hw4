package hw04.model.paintStrategies;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import hw04.model.IBall;
import provided.logger.demo.model.IModel2ViewAdapter;
import provided.utils.displayModel.IATImage;

public class ImagePaintStrategy extends APaintStrategy {

	/**
	 * The image to paint
	 */
	Image image;

	/**
	 * The image to paint
	 */
	IATImage iatImage;

	/**
	 * fill factor.
	 */
	double fillFactor = 0d;

	/**
	 * scale factor.
	 */
	double scaleFactor = 0d;
	
	/**
	 * The model's model2ViewAdapter, allowing creation of IATImages
	 */
	IModel2ViewAdapter model2ViewAdapter;



	/**
	 * An invariant "pre"-affine transform used to transform the image into its unit size
	 * and location.
	 */
	protected AffineTransform preAT = new AffineTransform();

	/**
	 * Temporary affine transform used to create the net virtual transformation from image to screen
	 * Transform every time the strategy paints.
	 */
	protected AffineTransform tempAT = new AffineTransform();

	/**
	 * @param at the affine transform obj
	 */
	public ImagePaintStrategy(AffineTransform at) {
		super(at);
	}

	/**
	 * No arg constructor
	 */
	public ImagePaintStrategy() {
		super(new AffineTransform());
	}

	@Override
	public void init(IBall host) {
//		this.model2ViewAdapter = host.getModel2ViewAdapter();
//		this.iatImage = this.model2ViewAdapter.getIATImage(this.image);
//		scaleFactor = 2.0 / (fillFactor * (iatImage.getWidth() + iatImage.getHeight()) / 2.0);
//		// Scale the image down to unit size.  Why do we do this last?
//		preAT.setToScale(scaleFactor, scaleFactor);
//		// First, center the image on the origin, assuming the displayed center is at the center of the image file.
//		preAT.translate(-iatImage.getWidth() / 2.0, -iatImage.getHeight() / 2.0);
	}


	/**
	 * @param filename the image file
	 * load the image file
	 */
	public void loadImg(String filename) {
		try {
			this.image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(filename));
		} catch (Exception e) {
			System.err.println("ImagePaintStrategy: Error reading file: " + filename + "\n" + e);
		}
	}

	@Override
	public void paintXfrm(Graphics g, IBall host, AffineTransform at) {
		tempAT.setTransform(preAT); // Initialize the tempAT to be the preAT, i.e. copy the preAT into the tempAT
		tempAT.preConcatenate(at); // Add the normal affine transform to the "pre"-affine transform.  The "preAT" will be applied first then the "at" when transforming an image.
		iatImage.draw(g, tempAT); // draw the IATImage image using the composed transform
		
	}


}
