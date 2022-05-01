package breakout;

/**
 * Represents the state of a block in the breakout game.
 *
 * @immutable
 * @invar | getLocation() != null
 */

public class NormalBlockState extends BlockState{
	
	/**
	 * Construct a block occupying a given rectangle in the field.
	 * @pre | location != null
	 * @post | getLocation().equals(location)
	 */
	public NormalBlockState(Rect location, int hits) {
		super(location, hits);
	}

	@Override 
	/**
	 * Return a copy of this block.
	 * @return
	 */
	public BlockState makeCopyBlock(int change) {
		return new NormalBlockState(getLocation(), getHits());
	}
}
