package breakout;

import java.awt.Color;

/**
 * Represents the state of a block in the breakout game.
 *
 * @immutable
 * @invar | getLocation() != null
 */
public abstract class BlockState {
	
	/**
	 * @invar | location != null
	 * @invar | hits >= 0
	 */
	private final Rect location;
	protected final int hits;
	
	/**
	 * Construct a block occupying a given rectangle in the field.
	 * @pre | location != null
	 * 
	 * @post | getLocation().equals(location)
	 */
	public BlockState(Rect location, int hits) {
		this.location = location;
		this.hits = hits;
	}

	/**
	 * Return the rectangle occupied by this block in the field.
	 */
	public Rect getLocation() {
		return location;
	}
	
	/**
	 * Return the number of times this block has been hit. 
	 * @return
	 */
	public int getHits() {
		return -1;
	}
	
	/**
	 * Return an object of class Ball.
	 * @param ball
	 * @creates | result
	 */
	public Ball makeSuper(Ball ball) {
		return ball;
	}
	
	/**
	 * Return an object of class PaddleState.
	 * @param paddle
	 * @creates | result
	 */
	public PaddleState makePaddle(PaddleState paddle) {
		return paddle;
	}
	
	/**
	 * Return this block's color.
	 * @return
	 */
	public Color getColor() {
		return Color.cyan;
	}
	
	/**
	 * Return a copy of this block.
	 * @return
	 */
	public BlockState makeCopyBlock(int change) {
		return this;
	}
}
