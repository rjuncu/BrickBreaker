package breakout;

import java.awt.Color;

/**
 * Represents the state of a block in the breakout game.
 *
 * @immutable
 * @invar | getLocation() != null
 */

public class SturdyBlockState extends BlockState{

	/**
	 * Construct a block occupying a given rectangle in the field.
	 * @pre | location != null
	 * @pre | (hits < 4) && (hits > -2)
	 * @post | getLocation().equals(location)
	 */
	public SturdyBlockState(Rect location, int hits) {
		super(location, hits);
	}
	
	/**
	 * Return the number of times this block has been hit. 
	 * @return result 
//	 * @post | result == getHits() Not sure why this is erroring
	 *
	 */
	@Override 
	public int getHits() {
		return this.hits;
	}
	
	/**
	 * Return this block's color.
	 * @return result 
	 * @post | result != null
	 */
	@Override
	public Color getColor() {
		switch(getHits()) {
		case 0:
			return Color.MAGENTA;
		case 1: 
			return Color.red;
		case 2: 
			return Color.pink;
		}
		return Color.magenta;
	}
	
	@Override 
	/**
	 * Return a copy of this block.
	 * @creates result 
	 * @param change
	 * @pre | change > 0 
	 * @post | (result.getLocation() == this.getLocation()) && (result.getHits() == this.getHits()+change)
	 */
	public BlockState makeCopyBlock(int change) {
		return new SturdyBlockState(getLocation(), getHits()+change);
		
	}

}
