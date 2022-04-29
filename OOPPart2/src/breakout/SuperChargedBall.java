package breakout;

public class SuperChargedBall extends Ball{
	
	int totalElapsedTime;

	public SuperChargedBall(Circle location, Vector velocity, int totalElapsedTime) {
		super(location, velocity);
		this.totalElapsedTime = totalElapsedTime; 
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
	
	//feed and return the ball instead?
	@Override
	public boolean checkElapsedTime(int elapsedtime) {
		this.totalElapsedTime = this.totalElapsedTime + elapsedtime;
		if (totalElapsedTime >= 10000) {
			return true;
		} else {
			return false;
	}
}}
