package breakout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubmissionTestSuite2 {

	private Ball[] oneBall;
	private BlockState block;
	private BlockState[] oneBlock;
	private Point bottomRight;
	private PaddleState paddle;
	private BreakoutState stateBeforeBounceBlock;
	private Ball ball;
	private BreakoutFacade facade = new BreakoutFacade();

	@BeforeEach
	void setUp() throws Exception {
		ball = facade.createNormalBall(new Point(1000, 1000), 100, new Vector(0, 5));
		oneBall = new Ball[] { ball };
		block = facade.createNormalBlockState(new Point(0, 1051), new Point(2000, 1200));
		oneBlock = new BlockState[] { block };
		bottomRight = new Point(10000, 2000);
		paddle = facade.createNormalPaddleState(new Point(2000, 1750));
		stateBeforeBounceBlock = facade.createBreakoutState(oneBall, oneBlock, bottomRight, paddle);
	}

	@Test
	void testTickBounceBlock() {
		stateBeforeBounceBlock.tick(1, 1);
		assertEquals(1, stateBeforeBounceBlock.getBalls().length);
		assertEquals(0, stateBeforeBounceBlock.getBlocks().length);
		assertEquals(new Vector(0, -5), stateBeforeBounceBlock.getBalls()[0].getVelocity());
	}

	@Test
	void testTickBounceBlock2() {
		BlockState block2 = facade.createPowerupBallBlockState(new Point(0, 1051), new Point(2000, 1200));
		BlockState block3 = facade.createNormalBlockState(new Point(0, 849), new Point(2000, 949));
		BlockState[] oneBlock2 = new BlockState[] { block2, block3 };
		BreakoutState stateBeforeBounceBlock2 = facade.createBreakoutState(oneBall, oneBlock2, bottomRight, paddle);
		stateBeforeBounceBlock2.tick(1, 1);
		assertEquals(1, stateBeforeBounceBlock2.getBalls().length);
		assertEquals(1, stateBeforeBounceBlock2.getBlocks().length);
		stateBeforeBounceBlock2.tick(1, 1);
		stateBeforeBounceBlock2.tick(1, 1);
		assertEquals(1, stateBeforeBounceBlock2.getBalls().length);
		assertEquals(0, stateBeforeBounceBlock2.getBlocks().length);
		assertEquals(new Vector(0, -5), stateBeforeBounceBlock2.getBalls()[0].getVelocity());
	}
}
