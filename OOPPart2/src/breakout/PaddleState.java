package breakout;

import java.awt.Color;

/**
 * Represents the state of a paddle in the breakout game.
 *
 * @immutable
 * @invar | getCenter() != null
 */
public class PaddleState {
	
	public static final int HEIGHT = 500;
	public static final int WIDTH = 3000;
	/**
	 * @invar | center != null
	 */
	private final Point center;
	//protected final int hits;

	/**
	 * Construct a paddle located around a given center in the field.
	 * @pre | center != null
	 * @post | getCenter().equals(center)
	 */
	public PaddleState(Point center) {
		this.center = center;
		//this.hits=hits;
	}
	
	/**
	 * Return the center point of this paddle.
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Return the rectangle occupied by this paddle in the field.
	 * @creates | result
	 * @post | result != null
	 * @post | result.getTopLeft().equals(getCenter().plus(new Vector(-WIDTH/2,-HEIGHT/2)))
	 * @post | result.getBottomRight().equals(getCenter().plus(new Vector(WIDTH/2,HEIGHT/2)))
	 */
	public Rect getLocation() {
		Vector halfDiag = new Vector(-WIDTH/2,-HEIGHT/2);
		return new Rect(center.plus(halfDiag), center.plus(halfDiag.scaled(-1)));
	}
	

	/**
	 * Create a new PaddleState object with a desired center.
	 * @param ncenter
	 * @param internalField
	 * @param hits
	 * @creates | result
	 * @post | result.getCenter().getX() == ncenter.getX()  
	 * @post | result.getCenter().getY() == ncenter.getY()
	 */
	public PaddleState setTypePaddle(Point ncenter, Rect internalField) {
		return new PaddleState(internalField.minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter));
	}
	
	/**
	 * Return a list of all the objects of type Ball.
	 * @param ball
	 * @param balls
	 * @return | balls
	 */
	public Ball[] replicateBall(Ball ball, Ball[] balls) {
		return balls;	
	};
	
	/**
	 * Return null since this paddle does not record hits.
	 * @return null
	 */
	public PaddleState incrementPaddleHits(PaddleState paddle) {
		return null;
	}
	
	/**
	 * Returns the color of this paddle.
	 * @return result
	 */
	public Color getColor() {
		return Color.red;
	}
	
	/**
	 * Return -1 since this paddle does not record hits.
	 * @return -1
	 */
	public int getHits() {
		return -1;
	}
}
