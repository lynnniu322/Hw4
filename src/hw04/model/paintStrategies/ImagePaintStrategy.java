package hw04.model.paintStrategies;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import hw04.model.IBall;
import hw04.model.IViewControlAdapter;
import provided.utils.displayModel.IATImage;

/**
 * The paint strategy that paints an image
 *
 */
public class ImagePaintStrategy extends APaintStrategy {

	/**
	 * The image to paint, raw
	 */
	Image image;

	/**
	 * The image to paint, processed
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
	IViewControlAdapter model2ViewAdapter;

	/**
	 * image observer
	 */
	protected Container imageObs;

	/**
	 * invariant "pre"-affine transform, used to transform the image into its unit size
	 * and location.
	 */
	protected AffineTransform pre_at = new AffineTransform();

	/**
	 * Temporary affine transform, transform every time the strategy paints.
	 */
	protected AffineTransform temp_at = new AffineTransform();

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
		this.model2ViewAdapter = host.getViewControlAdapter();
		imageObs = host.getCanvas();
		this.iatImage = IATImage.FACTORY.apply(image, imageObs);
		MediaTracker mt = new MediaTracker(host.getCanvas());
		mt.addImage(image, 1);
		try {
			mt.waitForAll();

		} catch (Exception e) {
			System.out.println("ImagePaintStrategy.init(): Error waiting for image.  Exception = " + e);
		}
		scaleFactor = 2.0 / (fillFactor * (iatImage.getWidth() + iatImage.getHeight()) / 2.0); // this line is described below

		// Scale the image down to unit size. 
		pre_at.setToScale(scaleFactor, scaleFactor);
		// First, center the image on the origin, assuming the displayed center is at the center of the image file.
		pre_at.translate(-iatImage.getWidth() / 2.0, -iatImage.getHeight() / 2.0);
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
	protected void paintCfg(Graphics g, IBall host) {
		super.paintCfg(g, host);
		if (Math.abs(Math.atan2(host.getVelocity().y, host.getVelocity().x)) > Math.PI / 2.0) {
			at.scale(1.0, -1.0);
		}
	}

	@Override
	public void paintXfrm(Graphics g, IBall host, AffineTransform at) {

		temp_at.setTransform(pre_at); // Initialize the temp_at to be the pre_at
		temp_at.preConcatenate(at); // apply pre_at first, then temp_at
		iatImage.draw(g, temp_at); // draw the IATImage image using the composed transform

	}

}
