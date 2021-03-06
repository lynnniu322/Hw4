package hw04.model.paintStrategies;

import java.awt.geom.AffineTransform;

/**
 *  paint strategy that paints a emoji
 *
 */
public class EmojiPaintStrategy extends ImagePaintStrategy {

	/**
	 * @param at AffineTransform
	 */
	public EmojiPaintStrategy(AffineTransform at) {
		super(at);
		this.loadImg("images/emoji.png");
		this.fillFactor = 1.0d;
	}

	/**
	 * Default constructor.
	 */
	public EmojiPaintStrategy() {
		this(new AffineTransform());
	}

}
