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
	 * @post | getLocation().equals(location)
	 */
	public SturdyBlockState(Rect location, int hits) {
		super(location, hits);
	}
	
	/**
	 * Return the number of times this block has been hit. 
	 * @return
	 */
	@Override 
	public int getHits() {
		return this.hits;
	}
	
	/**
	 * Return this block's color.
	 * @return
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
	 * @return
	 */
	public BlockState makeCopyBlock(int change) {
		//System.out.println(getHits());
//		if(getHits()==-1) {
//			return new SturdyBlockState(getLocation(), 1);
//		}else {
		//System.out.println(getHits()+change + " " + change);
//		int currentHits = getHits();
//		Rect newLocation = getLocation();
//		SturdyBlockState tempBlock = new SturdyBlockState(newLocation, currentHits+change);
//		System.out.println(tempBlock.getHits());
		return new SturdyBlockState(getLocation(), getHits()+change);
		
	}

}
