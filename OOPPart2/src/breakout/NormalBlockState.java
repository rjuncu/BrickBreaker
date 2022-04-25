package breakout;

public class NormalBlockState extends BlockState{

	public NormalBlockState(Rect location) {
		super(location);
	}

	@Override
	public char getBlockType() {
		return '#';
	}
}
