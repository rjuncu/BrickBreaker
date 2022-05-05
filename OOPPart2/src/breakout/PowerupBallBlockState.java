package breakout;

import java.awt.Color;

/**
 * Represents the state of a block in the breakout game.
 *
 * @immutable
 * @invar | getLocation() != null
 */
public class PowerupBallBlockState extends BlockState{

	/**
	 * Construct a block occupying a given rectangle in the field.
	 * @pre | location != null
	 * @pre | (hits < 4) && (hits > -2)
	 * @post | getLocation().equals(location)
	 */
	public PowerupBallBlockState(Rect location, int hits) {
		super(location, hits);
	}

	/**
	 * Return an object of class SuperChargedBall.
	 * @param ball
	 * @creates | result
	 * @pre | ball != null
	 * @post | (result.getLocation() == ball.getLocation()) && (result.getVelocity() == ball.getVelocity())
	 */
	@Override
	public Ball makeSuper(Ball ball) {
		return new SuperChargedBall(ball.getLocation(), ball.getVelocity(),0);
	}
	
	/**
	 * Return this block's color.
	 * @return | result 
	 * @post | result != null
	 */
	@Override
	public Color getColor() {
		return Color.green;
	}
	
	@Override 
	/**
	 * Return a copy of this block.
	 * @creates | result 
	 * @post | (result.getLocation() == this.getLocation()) && (result.getHits() == this.getHits())
	 */
	public BlockState makeCopyBlock(int change) {
		return new PowerupBallBlockState(getLocation(), getHits());
	}
	
	
}
