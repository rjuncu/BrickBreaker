package breakout;

import java.awt.Color;

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
	 * @pre | rect != null
	 * @post | (rect.collideWith(getLocation()) == null && result == null) ||
	 *       | (getVelocity().product(rect.collideWith(getLocation())) <= 0 && result == null) || 
	 *       | (result.equals(getVelocity().mirrorOver(rect.collideWith(getLocation()))))
	 */
	public Vector bounceOn(Rect rect, boolean notUsed) {
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
	
//	/**
//	 * Mirror this ball's velocity when colliding with a block.
//	 * @param rect
//	 * @param destroyed
//	 * @return
//	 */
//	 public Vector hitBlock(Rect rect, boolean destroyed) {
//		 System.out.println(bounceOn(rect));
//		 return bounceOn(rect);
//	 }
	 
	 /**
	  * Return false since this block object does not require a timer.
	  * @param elapsedtime
	  * @return
	  */
	 public boolean checkElapsedTime(int elapsedtime) {
			return false;
		}

	 /**
	  * Return this ball's color.
	  * @return
	  */
	 public Color getColor() {
		 return Color.magenta;
	 }
}
