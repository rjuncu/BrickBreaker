package breakout;

import java.awt.Color;

/**
 * Represents the state of a paddle in the breakout game.
 *
 * @immutable
 * @invar | getCenter() != null
 */
public class ReplicatorPaddleState extends PaddleState{

	private int hits; 

	/**
	 * @invar | center != null
	 */
	public ReplicatorPaddleState(Point center,int hits) {
		super(center);
		this.hits = hits;
	}

	/**
	 * Returns the number of hits this paddle has received.
	 */
	@Override
	public int getHits() {
		return this.hits;
	}

	/**
	 * Sets the type of this paddle to ReplicatorPaddleState if it contains less than 3 hits.
	 * @param ncenter 
	 * @param internalField
	 * @creates result
	 * @post | result.getCenter() == internalField.minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter)
	 */
	@Override 
	public PaddleState setTypePaddle(Point ncenter, Rect internalField) {
		if (getHits()<3) {
			return new ReplicatorPaddleState(internalField.minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter), getHits());
		} else {
			return new PaddleState(internalField.minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter));
		}
	}

	/**
	 * Return a list of new balls according to how many hits this paddle currently has.
	 * @param ball, balls 
	 * @mutates balls 
	 */
	@Override
	public Ball[] replicateBall(Ball ball, Ball[] balls) {
		switch(getHits()) {
		case 0: 
			balls =ball.createBalls(balls, new Vector(2,-2), ball);
			balls =ball.createBalls(balls, new Vector(-2,2), ball);
			balls =ball.createBalls(balls, new Vector(2,2), ball);
			//paddle = new ReplicatorPaddleState(paddle.getCenter(), paddle.getHits()+1);
			break;
		case 1: 
			balls =ball.createBalls(balls,new Vector(2, -2), ball);
			balls =ball.createBalls(balls,new Vector(-2, 2), ball);
			//hits++; 
			break;
		case 2: 
			balls=ball.createBalls(balls,new Vector(2, -2), ball);
			//hits++;
			break;
		}
		return balls;
	};

	/**
	 * Returns an object with an increase in number of hits by 1. 
	 * @param paddle
	 * @creates result 
	 * @post | result.getCenter() == paddle.getCenter()
	 */
	@Override
	public ReplicatorPaddleState incrementPaddleHits(PaddleState paddle) {
		return new ReplicatorPaddleState(paddle.getCenter(), paddle.getHits()+1);
	}
	
	/**
	 * Returns the color of this paddle.
	 * @return result
	 */
	@Override
	public Color getColor() {
		return Color.green;
	}

}
