package breakout;

import java.awt.Color;

/**
 * Represents the state of a ball in the breakout game.
 * 
 * @invar | getLocation() != null
 * @invar | getVelocity() != null
 */
public class SuperChargedBall extends Ball{

	private int totalElapsedTime;

	/**
	 * Construct a new ball at a given `location`, with a given `velocity`.
	 * 
	 * @pre | location != null
	 * @pre | velocity != null
	 * @pre | totalElapsedTime != -1
	 * @post | getLocation() == location
	 * @post | getVelocity().equals(velocity) 
	 */
	public SuperChargedBall(Circle location, Vector velocity, int totalElapsedTime) {
		super(location, velocity);
		this.totalElapsedTime = totalElapsedTime; 
	}
	
	/**
	 * Check whether this ball collides with a given `rect` and if so, return the 
	 * new velocity this ball will have after bouncing on the given rect. If the rect was not
	 * destroyed (destroyed == false) then this ball will return to normal state.
	 * 
	 * @pre | rect != null
	 * @pre | destroyed == true || destroyed == false
	 * @post | (rect.collideWith(getLocation()) == null && result == null) ||
	 *       | (getVelocity().product(rect.collideWith(getLocation())) <= 0 && result == null)
	 * @post | (destroyed == false) && (result.equals(getVelocity().mirrorOver(rect.collideWith(getLocation()))))
	 * @post | (destroyed == true) && (result.equals(getVelocity()))
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
//	public Vector bounceOn(Rect rect, boolean notUsed) {
//		Vector coldir = rect.collideWith(location);
//		if(coldir != null && velocity.product(coldir) > 0) {
//			return velocity.mirrorOver(coldir);
//		}
//		return null;

	//	@Override
	//	public Vector hitBlock(Rect rect, boolean destroyed) {
	//		if (destroyed == false) {
	//			return bounceOn(rect);
	//		}else {
	//			return getVelocity();
	//		}
	//	}

	//feed and return the ball instead?
	/**
	 * @param elaspedtime
	 * @return result 
	 * @post | (result == true) || (result == false)
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
	  * Return this ball's color.
	  * @return
	  */
	@Override
	public Color getColor() {
		return Color.orange;
	}
}
