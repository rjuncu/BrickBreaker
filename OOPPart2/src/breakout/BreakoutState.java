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
			Vector nspeed = b.bounceOn(wall);
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
	temptimer
	private Ball collideBallBlocks(Ball ball, int elapsedTime) {
		for(BlockState block : blocks) {
			Vector nspeed = ball.bounceOn(block.getLocation());
			if(nspeed != null) {
				if (block.getBlockType()=='S') {
					block.hitBlock(1);
				}
				if (block.getBlockType()=='!') {
					ball = new SuperChargedBall(ball.getLocation(), ball.getVelocity());
					//ball.startimer(elapsedtime)
				}
				if (block.getBlockType()=='R') {
					this.paddle=new ReplicatorPaddleState(this.paddle.getCenter());
				}
				boolean broken = removeBlock(block);
				nspeed = ball.hitBlock(block.getLocation(), broken);
				if(broken == false) {
					ball = new NormalBall(ball.getLocation(), ball.getVelocity());
				}
				ball.setVelocity(nspeed);
				return ball;
			}
		}
		return ball;
	}

	//EB: new paddle is created each time it is moved, resetting 'hits' to 0//
	private Ball collideBallPaddle(Ball ball, Vector paddleVel) {
		Vector nspeed = ball.bounceOn(this.paddle.getLocation());
		if(nspeed != null) {
			nspeed = nspeed.plus(paddleVel.scaledDiv(5));
			ball.setVelocity(nspeed);
			Point ncenter = ball.getLocation().getCenter().plus(nspeed);
			ball.setLocation(ball.getLocation().withCenter(ncenter));

			if(this.paddle.getPaddleType()=='R') {

				switch(this.paddle.getHits()) {
				case 0: 
					//					Ball newBall1 = new NormalBall(ball.getLocation(), ball.getVelocity().plus(new Vector(2,-2)));
					//					Ball newBall2 = new NormalBall(ball.getLocation(), ball.getVelocity().plus(new Vector(-2, 2)));
					//					Ball newBall3 = new NormalBall(ball.getLocation(), ball.getVelocity().plus(new Vector(2, 2)));
					//					ArrayList<Ball> tempBallsList1 = new ArrayList<Ball>(Arrays.asList(getBalls()));
					//					tempBallsList1.add(newBall1);
					//					tempBallsList1.add(newBall2);
					//					tempBallsList1.add(newBall3);
					//					this.balls = tempBallsList1.toArray(balls);
					//					this.paddle.hitPaddle(this.paddle.getHits());
					createBalls(new Vector(2,-2), ball);
					createBalls(new Vector(-2,2), ball);
					createBalls(new Vector(2,2), ball);
					this.paddle.hitPaddle();
					System.out.println(this.paddle.getHits());
					break;
				case 1: 
					//					Ball newBall4 = new NormalBall(ball.getLocation(), ball.getVelocity().plus(new Vector(2,-2)));
					//					Ball newBall5 = new NormalBall(ball.getLocation(), ball.getVelocity().plus(new Vector(-2, 2)));
					//					ArrayList<Ball> tempBallsList2 = new ArrayList<Ball>(Arrays.asList(getBalls()));
					//					tempBallsList2.add(newBall4);
					//					tempBallsList2.add(newBall5);
					//					this.balls = tempBallsList2.toArray(balls);
					//					this.paddle.hitPaddle(this.paddle.getHits());
					createBalls(new Vector(2, -2), ball);
					createBalls(new Vector(-2, 2), ball);
					this.paddle.hitPaddle();
					break;
				case 2: 
					//					Ball newBall6 = new NormalBall(ball.getLocation(), ball.getVelocity().plus(new Vector(2,-2)));
					//					ArrayList<Ball> tempBallsList3 = new ArrayList<Ball>(Arrays.asList(getBalls()));
					//					tempBallsList3.add(newBall6);
					//					this.balls = tempBallsList3.toArray(balls);
					//					this.paddle.hitPaddle(this.paddle.getHits());
					createBalls(new Vector(2, -2), ball);
					this.paddle = new PaddleState(this.paddle.getCenter());
					this.paddle.hitPaddle();
					break;
				default:
					//					this.paddle= new PaddleState(this.paddle.getCenter());
					break;
				}}


		}
		return ball;}

	private void createBalls(Vector speed, Ball originalBall) {
		Ball newBall = new NormalBall(originalBall.getLocation(), originalBall.getVelocity().plus(speed));
		ArrayList<Ball> tempBallsList = new ArrayList<Ball>(Arrays.asList(getBalls()));
		tempBallsList.add(newBall);
		this.balls = tempBallsList.toArray(balls);
	}

	private boolean removeBlock(BlockState block) {
		boolean broken = true;
		ArrayList<BlockState> nblocks = new ArrayList<BlockState>();
		if(block.getBlockType()=='S' && block.getHits()<3) {
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
	private long totalTime =0;
	public void tick(int paddleDir, int elapsedTime) {
		//System.out.println(elapsedTime);
		//ball.checktimer()
		totalTime = elapsedTime+totalTime;
		if(totalTime >=10000) {
		}
		stepBalls();
		bounceBallsOnWalls();
		removeDeadBalls();
		bounceBallsOnBlocks(elapsedTime);
		bounceBallsOnPaddle(paddleDir);
		clampBalls();
		balls = Arrays.stream(balls).filter(x -> x != null).toArray(Ball[]::new);
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

	private void bounceBallsOnBlocks(int elapsedTime) {
		for(int i = 0; i < balls.length; ++i) {
			if(balls[i] != null) {
				balls[i] = collideBallBlocks(balls[i], elapsedTime);
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
	//in order to keep the state of the paddle the same, i do type checking, might need to change this when we implement dynamic binding 
	public void movePaddleRight(int elapsedTime) {
		Point ncenter = this.paddle.getCenter().plus(PADDLE_VEL);
		if(this.paddle.getPaddleType()=='R') {
			ReplicatorPaddleState newPaddle = new ReplicatorPaddleState(getField().minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter));
			newPaddle.setHits(this.paddle.getHits());
			this.paddle = newPaddle;
			//			paddle = new ReplicatorPaddleState(getField().minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter));
		}else {
			this.paddle = new PaddleState(getField().minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter));
		}
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
		if(this.paddle.getPaddleType()=='R') {
			ReplicatorPaddleState newPaddle = new ReplicatorPaddleState(getField().minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter));
			newPaddle.setHits(this.paddle.getHits());
			this.paddle = newPaddle;
			//			paddle = new ReplicatorPaddleState(getField().minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter));
			//			paddle.setHits(paddle.getHits());

		}else { 
			this.paddle = new PaddleState(getField().minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter));
		}
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
