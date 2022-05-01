package breakout;

import java.awt.Color;

/**
 * Represents the state of a block in the breakout game.
 *
 * @immutable
 * @invar | getLocation() != null
 */
public class ReplicatorBlockState extends BlockState{

	/**
	 * Construct a block occupying a given rectangle in the field.
	 * @pre | location != null
	 * @post | getLocation().equals(location)
	 */
	public ReplicatorBlockState(Rect location, int hits) {
		super(location, hits);
	}
	
	/**
	 * Return an object of class ReplicatorPaddleState.
	 * @param paddle
	 * @creates | result
	 */
	@Override 
	public PaddleState makePaddle(PaddleState paddle) {
		return new ReplicatorPaddleState(paddle.getCenter(), paddle.getHits());
	}
	
	/**
	 * Return this block's color.
	 * @return
	 */
	@Override
	public Color getColor() {
		return Color.blue;
	}
	
	@Override 
	/**
	 * Return a copy of this block.
	 * @return
	 */
	public BlockState makeCopyBlock(int change) {
		return new ReplicatorBlockState(getLocation(), getHits());
	}
}
