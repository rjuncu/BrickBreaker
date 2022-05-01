package breakout;

/**
 * Represents the state of a block in the breakout game.
 *
 * @immutable
 * @invar | getLocation() != null
 */

public class NormalBlockState extends BlockState{
	
	/**
	 * Construct a normal block occupying a given rectangle in the field.
	 * @pre | location != null
	 * @pre | (hits < 4) && (hits > -2)
	 * @post | getLocation().equals(location)
	 */
	public NormalBlockState(Rect location, int hits) {
		super(location, hits);
	}

	@Override 
	/**
	 * Return a copy of this block.
	 * @creates result 
	 * @post | (result.getLocation() == this.getLocation()) && (result.getHits() == this.getHits())
	 */
	public BlockState makeCopyBlock(int change) {
		return new NormalBlockState(getLocation(), getHits());
	}
}
