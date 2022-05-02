package breakout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents the current state of a breakout game.
 *  
 * @invar | getBalls() != null
 * @invar | getBlocks() != null
 * @invar | getPaddle() != null
 * @invar | getBottomRight() != null
 * @invar | Point.ORIGIN.isUpAndLeftFrom(getBottomRight())
 * @invar | Arrays.stream(getBalls()).allMatch(b -> getField().contains(b.getLocation()))
 * @invar | Arrays.stream(getBlocks()).allMatch(b -> getField().contains(b.getLocation()))
 * @invar | getField().contains(getPaddle().getLocation())
 */
public class BreakoutState {

	private static final Vector PADDLE_VEL = new Vector(50,0);

	public static final int MAX_ELAPSED_TIME = 50;

	/**
	 * @invar | bottomRight != null
	 * @invar | Point.ORIGIN.isUpAndLeftFrom(bottomRight)
	 */
	private final Point bottomRight;
	/**
	 * @invar | balls != null
	 * @invar | Arrays.stream(balls).allMatch(b -> getFieldInternal().contains(b.getLocation()))
	 * @representationObject
	 */
	private Ball[] balls;	
	/**
	 * @invar | blocks != null
	 * @invar | Arrays.stream(blocks).allMatch(b -> getFieldInternal().contains(b.getLocation()))
	 * @representationObject
	 */
	private BlockState[] blocks;
	/**
	 * @invar | paddle != null
	 * @invar | getFieldInternal().contains(paddle.getLocation())
	 */
	private PaddleState paddle;

	private final Rect topWall;
	private final Rect rightWall;
	private final Rect leftWall;
	private final Rect[] walls;

	/**
	 * Construct a new BreakoutState with the given balls, blocks, paddle.
	 * 
	 * @throws IllegalArgumentException | balls == null
	 * @throws IllegalArgumentException | blocks == null
	 * @throws IllegalArgumentException | bottomRight == null
	 * @throws IllegalArgumentException | paddle == null
	 * @throws IllegalArgumentException | !Point.ORIGIN.isUpAndLeftFrom(bottomRight)
	 * @throws IllegalArgumentException | !(new Rect(Point.ORIGIN,bottomRight)).contains(paddle.getLocation())
	 * @throws IllegalArgumentException | !Arrays.stream(blocks).allMatch(b -> (new Rect(Point.ORIGIN,bottomRight)).contains(b.getLocation()))
	 * @throws IllegalArgumentException | !Arrays.stream(balls).allMatch(b -> (new Rect(Point.ORIGIN,bottomRight)).contains(b.getLocation()))
	 * @post | Arrays.equals(getBalls(),balls)
	 * @post | Arrays.equals(getBlocks(),blocks)
	 * @post | getBottomRight().equals(bottomRight)
	 * @post | getPaddle().equals(paddle)
	 */
	public BreakoutState(Ball[] balls, BlockState[] blocks, Point bottomRight, PaddleState paddle) {
		if( balls == null) throw new IllegalArgumentException();
		if( blocks == null) throw new IllegalArgumentException();
		if( bottomRight == null) throw new IllegalArgumentException();
		if( paddle == null) throw new IllegalArgumentException();

		if(!Point.ORIGIN.isUpAndLeftFrom(bottomRight)) throw new IllegalArgumentException();
		this.bottomRight = bottomRight;
		if(!getFieldInternal().contains(paddle.getLocation())) throw new IllegalArgumentException();
		if(!Arrays.stream(blocks).allMatch(b -> getFieldInternal().contains(b.getLocation()))) throw new IllegalArgumentException();
		if(!Arrays.stream(balls).allMatch(b -> getFieldInternal().contains(b.getLocation()))) throw new IllegalArgumentException();

		this.balls = balls.clone();
		this.blocks = blocks.clone();
		this.paddle = paddle;

		this.topWall = new Rect( new Point(0,-1000), new Point(bottomRight.getX(),0));
		this.rightWall = new Rect( new Point(bottomRight.getX(),0), new Point(bottomRight.getX()+1000,bottomRight.getY()));
		this.leftWall = new Rect( new Point(-1000,0), new Point(0,bottomRight.getY()));
		this.walls = new Rect[] {topWall,rightWall, leftWall };
	}

	/**
	 * Return the balls of this BreakoutState.
	 * 
	 * @creates result
	 */
	public Ball[] getBalls() {
		return balls.clone();
	}

	/**
	 * Return the blocks of this BreakoutState. 
	 *
	 * @creates result
	 */
	public BlockState[] getBlocks() {
		return blocks.clone();
	}

	/**
	 * Return the paddle of this BreakoutState. 
	 */
	public PaddleState getPaddle() {
		return paddle;
	}

	/**
	 * Return the point representing the bottom right corner of this BreakoutState.
	 * The top-left corner is always at Coordinate(0,0). 
	 */
	public Point getBottomRight() {
		return bottomRight;
	}

	// internal version of getField which can be invoked in partially inconsistent states
	private Rect getFieldInternal() {
		return new Rect(Point.ORIGIN, bottomRight);
	}

	/**
	 * Return a rectangle representing the game field.
	 * @post | result != null
	 * @post | result.getTopLeft().equals(Point.ORIGIN)
	 * @post | result.getBottomRight().equals(getBottomRight())
	 */
	public Rect getField() {
		return getFieldInternal();
	}

	private Ball bounceWalls(Ball b) {
		Circle loc = b.getLocation();
		for( Rect wall : walls) {
			Vector nspeed = b.bounceOn(wall, false);
			if( nspeed != null ) {
				b.setLocation(loc);
				b.setVelocity(nspeed);
				return b;
			}
		}
		return b;
	}

	private Ball removeDead(Ball ball) {
		if( ball.getLocation().getBottommostPoint().getY() > bottomRight.getY()) { return null; }
		else { return ball; }
	}

	private Ball clampBall(Ball b) {
		Circle loc = getFieldInternal().constrain(b.getLocation());
		b.setLocation(loc);
		return b;
	}
	
	private Ball collideBallBlocks(Ball ball) {
		for(int i = 0; i<blocks.length; i++) {
			Vector nspeed = ball.bounceOn(blocks[i].getLocation(), true);
			if(nspeed != null) {
				blocks[i] = blocks[i].makeCopyBlock(1);
				this.paddle = blocks[i].makePaddle(this.paddle);
				Ball newBall = blocks[i].makeSuper(ball);

				Rect tempLocation = blocks[i].getLocation();
				boolean broken = removeBlock(blocks[i]);
				
				nspeed = ball.bounceOn(tempLocation, broken);
				ball = newBall;
				
				//ball = blocks[i].makeSuper(ball);
				
//				if(broken == false) {
//					ball = new NormalBall(ball.getLocation(), ball.getVelocity());
//				}
				ball.setVelocity(nspeed);
				
				return ball;
			}
		}
		return ball;
	}

	private Ball collideBallPaddle(Ball ball, Vector paddleVel) {
		Vector nspeed = ball.bounceOn(this.paddle.getLocation(), false);
		if(nspeed != null) {
			nspeed = nspeed.plus(paddleVel.scaledDiv(5));
			ball.setVelocity(nspeed);
			Point ncenter = ball.getLocation().getCenter().plus(nspeed);
			ball.setLocation(ball.getLocation().withCenter(ncenter));
			int tempBallsLength = this.balls.length;
			this.balls=this.paddle.replicateBall(ball,this.balls);
			if(tempBallsLength < this.balls.length) {
				this.paddle = this.paddle.incrementPaddleHits(this.paddle);
			}
			this.paddle=this.paddle.setTypePaddle(this.paddle.getCenter(),getField());


		}
		return ball;
				}

	private boolean removeBlock(BlockState block) {
		boolean broken = true;
		ArrayList<BlockState> nblocks = new ArrayList<BlockState>();
		if(block.getHits()>-1 && block.getHits()<3) {
			nblocks.toArray(blocks.clone());
			broken = false;
		}else {
			for( BlockState b : blocks ) {
				if(b != block) {
					nblocks.add(b);
				}
			}
			blocks = nblocks.toArray(new BlockState[] {});
		}
		return broken;
	}

	/**
	 * Move all moving objects one step forward.
	 * 
	 * @mutates this
	 */
	//Make sure to go back and look at this today 
	public void tick(int paddleDir, int elapsedTime) {

		stepBalls();
		bounceBallsOnWalls();
		removeDeadBalls();
		bounceBallsOnBlocks();
		bounceBallsOnPaddle(paddleDir);
		clampBalls();
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				if (balls[i].checkElapsedTime(elapsedTime) == true) {
					this.balls[i] = new NormalBall(this.balls[i].getLocation(), this.balls[i].getVelocity());
				}
			}
		
		balls = Arrays.stream(balls).filter(x -> x != null).toArray(Ball[]::new);
		
	}
	}

	private void clampBalls() {
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				balls[i] = clampBall(balls[i]);
			}		
		}
	}

	private void bounceBallsOnPaddle(int paddleDir) {
		Vector paddleVel = PADDLE_VEL.scaled(paddleDir);
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				balls[i] = collideBallPaddle(balls[i], paddleVel);
			}
		}
	}

	private void bounceBallsOnBlocks() {
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				balls[i] = collideBallBlocks(balls[i]);
			}
		}
	}

	private void removeDeadBalls() {
		for(int i = 0; i < balls.length; ++i) {
			balls[i] = removeDead(balls[i]);
		}
	}

	private void bounceBallsOnWalls() {
		for(int i = 0; i < balls.length; ++i) {
			balls[i] = bounceWalls(balls[i]);
		}
	}

	private void stepBalls() {
		for(int i = 0; i < balls.length; ++i) {
			Point newcenter = balls[i].getLocation().getCenter().plus(balls[i].getVelocity());
			balls[i].setLocation(balls[i].getLocation().withCenter(newcenter));
		}
	}

	/**
	 * Move the paddle right.
	 * @param elapsedTime 
	 * 
	 * @mutates this
	 */
	public void movePaddleRight(int elapsedTime) {
		Point ncenter = this.paddle.getCenter().plus(PADDLE_VEL);
		PaddleState newPaddle = this.paddle.setTypePaddle(ncenter, getField());
		this.paddle = newPaddle;
	}

	/**
	 * Move the paddle left.
	 * @param elapsedTime 
	 * 
	 * @mutates this
	 */
	//in order to keep the state of the paddle the same, i do type checking, might need to change this when we implement dynamic binding 
	public void movePaddleLeft(int elapsedTime) {
		Point ncenter = this.paddle.getCenter().plus(PADDLE_VEL.scaled(-1));
		PaddleState newPaddle = this.paddle.setTypePaddle(ncenter, getField());
		this.paddle = newPaddle;
	}

	/**
	 * Return whether this BreakoutState represents a game where the player has won.
	 * 
	 * @post | result == (getBlocks().length == 0 && !isDead())
	 * @inspects this
	 */
	public boolean isWon() {
		return getBlocks().length == 0 && !isDead();
	}

	/**
	 * Return whether this BreakoutState represents a game where the player is dead.
	 * 
	 * @post | result == (getBalls().length == 0)
	 * @inspects this
	 */
	public boolean isDead() {
		return getBalls().length == 0;
	}
}
