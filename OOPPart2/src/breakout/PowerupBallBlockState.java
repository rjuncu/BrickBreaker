package breakout;

public class PowerupBallBlockState extends BlockState{

	public PowerupBallBlockState(Rect location) {
		super(location);
	}
	
	@Override
	public char getBlockType() {
		return '!';
	}
}
