package breakout;

import java.util.ArrayList;

public class GameMap {

	private static final int INIT_BALL_DIAMETER = 700;
	private static final int HEIGHT = 30000;
	private static final int WIDTH = 50000;
	private static int BLOCK_LINES = 8;
	private static int BLOCK_COLUMNS = 10;
	private static final Vector INIT_BALL_VELOCITY = new Vector(24, 44);
	//velocities were squared, but ball speed did not change
	private static BreakoutFacade facade = new BreakoutFacade();

	private GameMap() {
		throw new AssertionError("This class is not intended to be instantiated");
	}

	private static BlockState createBlock(Point bottomLeft, char type) {
		Vector marginBL = new Vector(20, 20);
		Vector size = new Vector(WIDTH / BLOCK_COLUMNS - 70, HEIGHT / BLOCK_LINES - 70);
		Point blockTL = bottomLeft.plus(marginBL);
		Point blockBR = blockTL.plus(size);
		switch (type) {
		case '#':
			return facade.createNormalBlockState(blockTL, blockBR);
		case 'S':
			return facade.createSturdyBlockState(blockTL, blockBR, 3);
		case 'R':
			return facade.createReplicatorBlockState(blockTL, blockBR);
		case '!':
			return facade.createPowerupBallBlockState(blockTL, blockBR);
		default:
			return null;
		}
	}

	private static PaddleState createPaddle(Point bottomLeft) {
		Vector size = new Vector(WIDTH / BLOCK_COLUMNS / 2, HEIGHT / BLOCK_LINES / 2);
		Point center = bottomLeft.plus(size);
		return facade.createNormalPaddleState(center);
	}

	private static Ball createBall(Point bottomLeft) {
		Vector centerD = new Vector(WIDTH / BLOCK_COLUMNS / 2, HEIGHT / BLOCK_LINES / 2);
		Point center = bottomLeft.plus(centerD);
		int diameter = INIT_BALL_DIAMETER;
		return facade.createNormalBall(center, diameter, INIT_BALL_VELOCITY);
	}

	/**
	 * Return the initial breakout state represented by string `description`.
	 * 
	 * @pre | description != null
	 * @post | result != null
	 */
	public static BreakoutState createStateFromDescription(String description) {
		String[] lines = description.split("\n", BLOCK_LINES);

		Vector unitVecRight = new Vector(WIDTH / BLOCK_COLUMNS, 0);
		Vector unitVecDown = new Vector(0, HEIGHT / BLOCK_LINES);
		ArrayList<BlockState> blocks = new ArrayList<BlockState>();
		ArrayList<Ball> balls = new ArrayList<Ball>();
		PaddleState paddle = null;

		Point topLeft = new Point(0, 0);
		assert lines.length <= BLOCK_LINES;
		for (String line : lines) {
			assert line.length() <= BLOCK_COLUMNS;
			Point cursor = topLeft;
			for (char c : line.toCharArray()) {
				switch (c) {
				case '#':
					blocks.add(createBlock(cursor, '#'));
					break;
				case 'S':
					blocks.add(createBlock(cursor, 'S'));
					break;
				case 'R':
					blocks.add(createBlock(cursor, 'R'));
					break;
				case '!':
					blocks.add(createBlock(cursor, '!'));
					break;
				case 'o':
					balls.add(createBall(cursor));
					break;
				case '=':
					paddle = createPaddle(cursor);
					break;
				}
				cursor = cursor.plus(unitVecRight);
			}
			topLeft = topLeft.plus(unitVecDown);
		}
		Point topRight = new Point(WIDTH, HEIGHT);

		return facade.createBreakoutState(balls.toArray(new Ball[] {}), blocks.toArray(new BlockState[] {}),
				topRight, paddle);
	}
}
