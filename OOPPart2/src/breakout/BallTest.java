package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BallTest {
	int twelvek;
	int ten;
	
	Point p11;
	Point p05;
	Point p38;
	Point pm14;
	
	Rect r1138;
	Rect rm1438;
	
	Vector v1010;
	
	Circle c052;
	Circle c389;
	NormalBall n1;
	SuperChargedBall s1;
	
	Ball[] balls;
	
	@BeforeEach
	void setUp() throws Exception {
		twelvek = 12000;
		ten = 10;
		p11 = new Point(1,1);
		p05 = new Point(0,5);
		p38 = new Point(3,8);
		pm14 = new Point(-1,4);
		r1138 = new Rect(p11,p38);
		rm1438 = new Rect(pm14,p38);
		c052 = new Circle(p05,2);
		c389 = new Circle(p38,9);
		v1010 = new Vector(10,10);
		n1 = new NormalBall(c052, v1010);
		s1 = new SuperChargedBall(c389, v1010,ten);
		balls = new Ball[] {n1,s1};
		
	}

	@Test
	void testBall() {
		assertEquals(p05, n1.getLocation().getCenter());
		assertEquals(2, n1.getLocation().getDiameter());
		assertEquals(v1010, n1.getVelocity());
	}

	@Test
	void testBounceOn() {
		assertEquals(new Vector(-10,10),n1.bounceOn(r1138));
	}
	
	@Test
	void testCheckElapsedTime() {
		assertTrue(n1.checkElapsedTime(ten)==false);
		assertFalse(n1.checkElapsedTime(twelvek)==true);
		assertTrue(s1.checkElapsedTime(ten)==false);
		assertTrue(s1.checkElapsedTime(twelvek)==true);
	}
	
	@Test
	void testCreateBalls() {
		assertEquals(balls.length + 1, (n1.createBalls(balls,v1010,n1)).length);
		
	}

}
