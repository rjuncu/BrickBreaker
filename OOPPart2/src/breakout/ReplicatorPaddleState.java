package breakout;

public class ReplicatorPaddleState extends PaddleState{

	private int hits = 0;
	public ReplicatorPaddleState(Point center) {
		super(center);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public char getPaddleType() {
		return 'R';
	}
	
	
	@Override
	public void hitPaddle() {
		this.hits = this.hits+1;
	}
	
	@Override
	public int getHits() {
		return this.hits;
	}
	
	@Override
	public void setHits(int hits) {
		this.hits = hits;
	}

}
