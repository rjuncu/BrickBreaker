package breakout;

public class ReplicatorBlockState extends BlockState{

	public ReplicatorBlockState(Rect location) {
		super(location);
	}

	@Override
	public char getBlockType() {
		return 'R';
	}
}
