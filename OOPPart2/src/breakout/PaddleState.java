package breakout;

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

	/**
	 * Construct a paddle located around a given center in the field.
	 * @pre | center != null
	 * @post | getCenter().equals(center)
	 */
	public PaddleState(Point center) {
		this.center = center;
	}
	
	/**
	 * Return the center point of this paddle.
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Return the rectangle occupied by this paddle in the field.
	 * 
	 * @post | result != null
	 * @post | result.getTopLeft().equals(getCenter().plus(new Vector(-WIDTH/2,-HEIGHT/2)))
	 * @post | result.getBottomRight().equals(getCenter().plus(new Vector(WIDTH/2,HEIGHT/2)))
	 */
	public Rect getLocation() {
		Vector halfDiag = new Vector(-WIDTH/2,-HEIGHT/2);
		return new Rect(center.plus(halfDiag), center.plus(halfDiag.scaled(-1)));
	}
	
	public char getPaddleType() {
		return 'N';
	}
	public void hitPaddle(int hit) {
	}
	
	public int getHits() {
		return 0;
	}
}
