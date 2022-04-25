package breakout;

public class SturdyBlockState extends BlockState{
	private int hits;
	public SturdyBlockState(Rect location) {
		super(location);
	}
	
	@Override
	public char getBlockType() {
		return 'S';
	}
	
	@Override
	public void hitBlock(int hit) {
		this.hits++;
	}
	
	@Override 
	public int getHits() {
		return this.hits;
	}

}
