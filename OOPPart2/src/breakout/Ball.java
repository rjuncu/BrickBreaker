package breakout;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the state of a ball in the breakout game.
 * 
 * @invar | getLocation() != null
 * @invar | getVelocity() != null
 */
public abstract class Ball {
	protected Circle location;
	protected Vector velocity;
	
	/**
	 * Construct a new ball at a given `location`, with a given `velocity`.
	 * 
	 * @pre | location != null
	 * @pre | velocity != null
	 * @post | getLocation() == location
	 * @post | getVelocity().equals(velocity) 
	 */
	public Ball(Circle location, Vector velocity) {
		this.location = location;
		this.velocity = velocity;
	}
	
	/**
	 * Return this ball's location.
	 */
	public Circle getLocation() {
		return location;
	}

	/**
	 * Return this ball's velocity.
	 */
	public Vector getVelocity() {
		return velocity;
	}
	
	/**
	 * Set this ball's location.
	 * @param newCircle
	 * @mutates | this.getLocation()
	 */
	public void setLocation(Circle newCircle) {
		this.location = newCircle;
	}
	
	/**
	 * Set this ball's velocity.
	 * @param newVelocity
	 * @mutates | this.getVelocity()
	 */
	public void setVelocity(Vector newVelocity) {
		this.velocity = newVelocity;
	}

	/**
	 * Check whether this ball collides with a given `rect` and if so, return the 
	 * new velocity this ball will have after bouncing on the given rect.
	 * 
	 * @param rect
	 * @param destroyed
	 * @pre | rect != null
	 * @pre | destroyed == true || destroyed == false
	 * 
	 * @post | result == null ? rect.collideWith(getLocation()) == null || getVelocity().product(rect.collideWith(getLocation())) <= 0
	 * 		| : result != null
	 * 
//	 * @post | rect.collideWith(getLocation()) == null && result == null ||
//	 *       | (getVelocity().product(rect.collideWith(getLocation())) <= 0 && result == null || 
//	 *       | (result.equals(getVelocity().mirrorOver(rect.collideWith(getLocation()))))
	 */
	public Vector bounceOn(Rect rect, boolean destroyed) {
		Vector coldir = rect.collideWith(location);
		if(coldir != null && velocity.product(coldir) > 0) {
			return velocity.mirrorOver(coldir);
		}
		return null;
	}

	/**
	 * Return this point's center.
	 * @post | getLocation().getCenter().equals(result)
	 */
	public Point getCenter() {
		return getLocation().getCenter();
	}

	 
	 /**
	  * Return false since this block object does not require a timer.
	  * @param elapsedtime
	  * @return | false
	  */
	 public boolean checkElapsedTime(int elapsedtime) {
			return false;
		}
	 
	 /**
	  * 
	  * @param balls
	  * @param speed
	  * @param originalBall
	  * @pre | balls != null
	  * @pre | speed != null
	  * @pre | originalBall != null
	  * @post | Arrays.equals(balls, 0, old(balls).length, old(balls), 0, old(balls).length)
	  */
	 public Ball[] createBalls(Ball[] balls, Vector speed, Ball originalBall) {
			Ball newBall = new NormalBall(originalBall.getLocation(), originalBall.getVelocity().plus(speed));
			ArrayList<Ball> tempBallsList = new ArrayList<Ball>(Arrays.asList(balls));
			tempBallsList.add(newBall);
			balls = tempBallsList.toArray(balls);
			return balls;
		}

	 /**
	  * Return this ball's color.
	  * @return | result
	  */
	 public Color getColor() {
		 return Color.magenta;
	 }
}
