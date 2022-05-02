package breakout;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the state of a ball in the breakout game.
 * 
 * @invar | getLocation() != null
 * @invar | getVelocity() != null
 * @invar | getTotalElapsedTime() >= 0
 */
public class SuperChargedBall extends Ball{

	private int totalElapsedTime;

	/**
	 * Construct a new ball at a given `location`, with a given `velocity`.
	 * 
	 * @pre | location != null
	 * @pre | velocity != null
	 * @pre | totalElapsedTime >= 0
	 * @post | getLocation() == location
	 * @post | getVelocity().equals(velocity) 
	 * @post | getTotalElapsedTime() == totalElapsedTime
	 */
	public SuperChargedBall(Circle location, Vector velocity, int totalElapsedTime) {
		super(location, velocity);
		this.totalElapsedTime = totalElapsedTime; 
	}
	
	/**
	 * Return the total elapsed time of the supercharged ball
	 */
	public int getTotalElapsedTime() {
		return totalElapsedTime;
	}
	
	/**
	 * Check whether this superchargedball collides with a given `rect` and if so, return the 
	 * new velocity for the ball. If the rect was not destroyed (destroyed == false) then this 
	 * ball will bounce back. If the rect was destroyed, the velocity remains unchanged.
	 * 
	 * @pre | rect != null
	 * @pre | destroyed == true || destroyed == false
	 * @post | rect.collideWith(getLocation()) == null || getVelocity().product(rect.collideWith(getLocation())) <= 0
	 * 			| ? result == null : result != null
//	 * @post | result != null && destroyed == false ? result.equals(getVelocity().mirrorOver(rect.collideWith(getLocation()))) 
//	 *			|: (result.equals(getVelocity()))
//	 * @post | rect.collideWith(getLocation()) != null && getVelocity().product(rect.collideWith(getLocation())) > 0  
//	 * 		|	? result.equals(getVelocity())|| result.equals(getVelocity().product(rect.collideWith(getLocation()))) 
//	 * 		|	: result.equals(null)
	 * 
	 */
	@Override
	public Vector bounceOn(Rect rect, boolean destroyed) {
		Vector coldir = rect.collideWith(location);
		Vector newVelocity = new Vector(0,0);
		if(coldir != null && velocity.product(coldir) > 0) {
			if (destroyed == false) {
				newVelocity = velocity.mirrorOver(coldir);
			}
			else {
				newVelocity = velocity;
			}
			return newVelocity;
		}
		return null;
	}

	/**
	 * Returns true if the elapsed time is over 10seconds and false if not.
	 * @param elaspedtime
	 * @pre | elapsedtime >= 0 
	 * @post | (result == true) || (result == false)
	 * @post | getTotalElapsedTime() >= 10000 ? result==true : result==false
	 * @return boolean
	 * 
	 */
	@Override
	public boolean checkElapsedTime(int elapsedtime) {
		this.totalElapsedTime = this.totalElapsedTime + elapsedtime;
		if (totalElapsedTime >= 10000) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Adds a new ball to the balls array and returns it.
	 * @param balls
	 * @param speed
	 * @param originalBall
	 * @pre balls != null
	 * @pre speed != null
	 * @pre originalBall != null
	 * @post new(balls.length) == old(balls.length)+1
	 * 
	 * 
	 */
	@Override
	public Ball[] createBalls(Ball[] balls, Vector speed, Ball originalBall) {
			Ball newBall = new SuperChargedBall(originalBall.getLocation(), originalBall.getVelocity().plus(speed), totalElapsedTime);
			ArrayList<Ball> tempBallsList = new ArrayList<Ball>(Arrays.asList(balls));
			tempBallsList.add(newBall);
			balls = tempBallsList.toArray(balls);
			return balls;
	}
		

	/**
	  * Return this ball's color.
	  * @return
	  */
	@Override
	public Color getColor() {
		return Color.orange;
	}
}
