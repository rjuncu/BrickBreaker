package breakout;

import java.awt.Color;

//No documentation required for this class
public class BreakoutFacade {
	public PaddleState createNormalPaddleState(Point center) {
		return new PaddleState(center);
	}

	public Ball createNormalBall(Point center, int diameter, Vector initBallVelocity) {
		Circle newCircle = new Circle(center, diameter);
		return new NormalBall(newCircle,initBallVelocity);
	}

	public Ball createSuperchargedBall(Point center, int diameter, Vector initBallVelocity, int lifetime) {
		Circle newCircle = new Circle(center, diameter);
		return new SuperChargedBall(newCircle,initBallVelocity);
	}

	public BreakoutState createBreakoutState(Ball[] balls, BlockState[] blocks, Point bottomRight,
			PaddleState paddle) {
		return new BreakoutState(balls, blocks, bottomRight, paddle);
	}

	public BlockState createNormalBlockState(Point topLeft, Point bottomRight) {
		Rect location = new Rect(topLeft, bottomRight);
		return new NormalBlockState(location);
	}

	public BlockState createSturdyBlockState(Point topLeft, Point bottomRight, int i) {
		Rect location = new Rect(topLeft, bottomRight);
		return new SturdyBlockState(location);
	}

	public BlockState createReplicatorBlockState(Point topLeft, Point bottomRight) {
		Rect location = new Rect(topLeft, bottomRight);
		return new ReplicatorBlockState(location);
	}

	public BlockState createPowerupBallBlockState(Point topLeft, Point bottomRight) {
		Rect location = new Rect(topLeft, bottomRight);
		return new PowerupBallBlockState(location);
	}

	public Color getColor(PaddleState paddle) {
		if(paddle.getPaddleType()=='R') {
			return Color.green;
		}else {
			return Color.red;
		}
	}

	public Color getColor(Ball ball) {
		if(ball.getBallType()=='S') {
			return Color.orange;
		}else {
			return Color.magenta;
		}
		//return Color.MAGENTA;
	}

	public Rect getLocation(PaddleState paddle) {
		// TODO
		return paddle.getLocation();
	}

	public Point getCenter(Ball ball) {
		// TODO
		return ball.getLocation().getCenter();
	}

	public int getDiameter(Ball ball) {
		// TODO
		return 500;
	}

	public Ball[] getBalls(BreakoutState breakoutState) {
		// TODO
		return breakoutState.getBalls();
	}

	public Color getColor(BlockState block) {
		switch(block.getBlockType()) {
		case 'S': 
			switch(block.getHits()) {
			case 0:
				return Color.MAGENTA;
			case 1: 
				return Color.red;
			case 2: 
				return Color.pink;
			}
		case '!': 
			return Color.green;
		case 'R':
			return Color.blue;
		case '#':
			return Color.cyan;
			
		}
		return null;
		

	}

	public Rect getLocation(BlockState block) {
		// TODO
		return block.getLocation();
	}
}
