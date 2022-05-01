package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaddleTest {
	Point p11;
	Point ncenter;
	PaddleState p1;
	ReplicatorPaddleState rp1;
	Rect iF;
	Ball b1;
	Ball[] balls;

	@BeforeEach
	void setUp() throws Exception {
		p11 = new Point(1,1);
		p1 = new PaddleState(p11);
		rp1 = new ReplicatorPaddleState(p11, 0);
		iF = new Rect(new Point(0,0), new Point(50000,30000));
		ncenter = new Point(27550,24375);
		b1 = new NormalBall(new Circle(p11,9), new Vector(10,10));
		balls = new Ball[] {b1};
	}

	@Test
	void testPaddle() {
		assertEquals(p11, p1.getCenter());
		assertEquals(p11, rp1.getCenter());
	}

	@Test
	void testGetLocation() {
		assertEquals(new Point(1-PaddleState.WIDTH/2,1-PaddleState.HEIGHT/2), p1.getLocation().getTopLeft());
		assertEquals(new Point(1+PaddleState.WIDTH/2,1+PaddleState.HEIGHT/2), p1.getLocation().getBottomRight());
	}
	
	@Test
	void testSetTypePaddle() {
		assertEquals(iF.minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter), p1.setTypePaddle(ncenter, iF).getCenter());
	}
	
	@Test
	void testReplicateBall() {
		assertEquals(balls, p1.replicateBall(b1, balls));
	}
	
	@Test
	void testIncrementPaddleHits() {
		assertEquals(rp1.getCenter(), rp1.incrementPaddleHits(rp1).getCenter());
		assertEquals(1, rp1.incrementPaddleHits(rp1).getHits());
	}
	
	@Test 
	void testGetHits() {
		assertEquals(0, rp1.getHits());
	}

}
