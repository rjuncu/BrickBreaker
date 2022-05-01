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
		return new SuperChargedBall(newCircle,initBallVelocity,0);
	}

	public BreakoutState createBreakoutState(Ball[] balls, BlockState[] blocks, Point bottomRight,
			PaddleState paddle) {
		return new BreakoutState(balls, blocks, bottomRight, paddle);
	}

	public BlockState createNormalBlockState(Point topLeft, Point bottomRight) {
		Rect location = new Rect(topLeft, bottomRight);
		return new NormalBlockState(location, 0);
	}

	public BlockState createSturdyBlockState(Point topLeft, Point bottomRight, int i) {
		Rect location = new Rect(topLeft, bottomRight);
		return new SturdyBlockState(location, 0);
	}

	public BlockState createReplicatorBlockState(Point topLeft, Point bottomRight) {
		Rect location = new Rect(topLeft, bottomRight);
		return new ReplicatorBlockState(location, 0);
	}

	public BlockState createPowerupBallBlockState(Point topLeft, Point bottomRight) {
		Rect location = new Rect(topLeft, bottomRight);
		return new PowerupBallBlockState(location, 0);
	}

	public Color getColor(PaddleState paddle) {
		return paddle.getColor();
	}

	public Color getColor(Ball ball) {
		return ball.getColor();
	}

	public Rect getLocation(PaddleState paddle) {
		return paddle.getLocation();
	}

	public Point getCenter(Ball ball) {
		return ball.getLocation().getCenter();
	}

	public int getDiameter(Ball ball) {
		return 500;
	}

	public Ball[] getBalls(BreakoutState breakoutState) {
		return breakoutState.getBalls();
	}

	public Color getColor(BlockState block) {
		return block.getColor();
	}

	public Rect getLocation(BlockState block) {
		return block.getLocation();
	}
}
