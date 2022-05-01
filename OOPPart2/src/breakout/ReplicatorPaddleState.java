package breakout;

import java.awt.Color;

public class ReplicatorPaddleState extends PaddleState{
	
	private int hits; 
	

	public ReplicatorPaddleState(Point center,int hits) {
		super(center);
		this.hits = hits;
	}
	
	
	public int getHits() {
		return this.hits;
	}
	
	
	@Override 
	public PaddleState setTypePaddle(Point ncenter, Rect internalField) {
		if (getHits()<3) {
		return new ReplicatorPaddleState(internalField.minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter), getHits());
	} else {
		return new PaddleState(internalField.minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter));
	}}
	
	
	@Override
	public Ball[] replicateBall(Ball ball, Ball[] balls) {
		switch(getHits()) {
		case 0: 
			balls =ball.createBalls(balls, new Vector(2,-2), ball);
			balls =ball.createBalls(balls, new Vector(-2,2), ball);
			balls =ball.createBalls(balls, new Vector(2,2), ball);
			hits++;
			break;
		case 1: 
			balls =ball.createBalls(balls,new Vector(2, -2), ball);
			balls =ball.createBalls(balls,new Vector(-2, 2), ball);
			hits++; 
			break;
		case 2: 
			balls=ball.createBalls(balls,new Vector(2, -2), ball);
			hits++;
			break;
		}
		return balls;
	};
	
	@Override
	public Color getColor() {
		return Color.green;
	}

}
