package breakout;

public class SuperChargedBall extends Ball{

	public SuperChargedBall(Circle location, Vector velocity) {
		super(location, velocity);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	 public Vector hitBlock(Rect rect, boolean destroyed) {
		if (destroyed == false) {
			 return bounceOn(rect);
		}else {
			return getVelocity();
		}
	}
	
	@Override
	public char getBallType() {
		return 'S';
	}
	
	@Override
	public Boolean checkElapsedTime(int elapsedtime) {
		
	}
	

}
