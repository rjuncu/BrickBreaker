package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaddleTest {
	Point p11;
	Point ncenter;
	Point ppd;
	PaddleState p1;
	PaddleState p2;
	ReplicatorPaddleState rp1;
	ReplicatorPaddleState rp11;
	ReplicatorPaddleState rp12;
	ReplicatorPaddleState rp3;
	ReplicatorPaddleState rp4;
	Rect iF;
	Ball b1;
	Ball[] balls;

	@BeforeEach
	void setUp() throws Exception {
		p11 = new Point(1,1);
		ppd = new Point(2500,28125);
		p1 = new PaddleState(p11);
		p2 = new PaddleState(ppd);
		rp1 = new ReplicatorPaddleState(p11, 0);
		rp11 = new ReplicatorPaddleState(p11,1);
		rp12 = new ReplicatorPaddleState(p11,2);
		rp3 = new ReplicatorPaddleState(ppd,2);
		rp4 = new ReplicatorPaddleState(ppd,4);
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
		assertEquals(iF.minusMargin(PaddleState.WIDTH/2,0).constrain(ppd), p2.setTypePaddle(p2.getCenter(), iF).getCenter());
		assertEquals(rp3.setTypePaddle(ppd, iF).getCenter(), new ReplicatorPaddleState(iF.minusMargin(PaddleState.WIDTH/2,0).constrain(ppd), 2).getCenter());
		assertEquals(rp4.setTypePaddle(ppd, iF).getCenter(), new PaddleState(iF.minusMargin(PaddleState.WIDTH/2,0).constrain(ppd)).getCenter());
	}
	
	@Test
	void testReplicateBall() {
		assertEquals(balls, p1.replicateBall(b1, balls));
		assertEquals(rp1.replicateBall(b1,balls).length,balls.length+3);
		assertEquals(rp11.replicateBall(b1,balls).length,balls.length+2);
		assertEquals(rp12.replicateBall(b1,balls).length,balls.length+1);
	}
	
	@Test
	void testIncrementPaddleHits() {
		assertEquals(rp1.getCenter(), rp1.incrementPaddleHits(rp1).getCenter());
		assertEquals(1, rp1.incrementPaddleHits(rp1).getHits());
		assertEquals(null, p1.incrementPaddleHits(p1));
	}
	
	@Test 
	void testGetHits() {
		assertEquals(0, rp1.getHits());
		assertEquals(-1, p1.getHits());
	}
	
	@Test 
	void testGetColor() {
		assertEquals(Color.green, rp1.getColor());
		assertEquals(Color.red, p1.getColor());
	}
	

}
