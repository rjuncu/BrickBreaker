package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlockTest {
	int hm1;
	int h1;
	
	Point p11;
	Point p05;
	Point p38;
	Point pm14;
	
	Vector v45;
	Vector vn56;
	
	Rect r1138;
	Rect rm1438;
	
	PaddleState pp11;
	ReplicatorPaddleState rpp11;
	
	NormalBall ball1;
	SuperChargedBall sball1;
	
	NormalBlockState nb1;
	SturdyBlockState sb1;
	SturdyBlockState sb2;
	SturdyBlockState sb0; 
	SturdyBlockState sb3;
	PowerupBallBlockState pbb1;
	ReplicatorBlockState rb1;

	@BeforeEach
	void setUp() throws Exception {
		hm1 = -1; 
		h1 = 1;
		p11 = new Point(1,1);
		p05 = new Point(0,5);
		p38 = new Point(3,8);
		pm14 = new Point(-1,4);
		v45 = new Vector(4,5);
		vn56 = new Vector(-5,6);
		r1138 = new Rect(p11,p38);
		rm1438 = new Rect(pm14,p38);
		pp11 = new PaddleState(p11);
		rpp11 = new ReplicatorPaddleState(p11, 0);
		nb1 = new NormalBlockState(r1138, hm1);
		sb1 = new SturdyBlockState(r1138, h1);
		sb2 = new SturdyBlockState(r1138, 2);
		sb0 = new SturdyBlockState(r1138, 0);
		sb3 = new SturdyBlockState(r1138, 3);
		pbb1 = new PowerupBallBlockState(rm1438, hm1);
		rb1 = new ReplicatorBlockState(rm1438, hm1);
		ball1 = new NormalBall(new Circle(p05,5),v45);
		sball1 = new SuperChargedBall(new Circle(p05,5),vn56,0);
	}

	@Test
	void testBlock() {
		assertEquals(r1138,nb1.getLocation());
		assertEquals(hm1, nb1.getHits());
		
		assertEquals(r1138, sb1.getLocation());
		assertEquals(h1, sb1.getHits());
		
		assertEquals(rm1438, pbb1.getLocation());
		assertEquals(hm1, pbb1.getHits());
		
		assertEquals(rm1438, rb1.getLocation());
		assertEquals(hm1, rb1.getHits());
	}
	
	@Test
	void testMakePaddle() {
		assertEquals(pp11, nb1.makePaddle(pp11));
		assertEquals(rpp11.getHits(), rb1.makePaddle(pp11).getHits());	
		assertEquals(rpp11.getLocation(), rb1.makePaddle(pp11).getLocation());	
	}
	
	@Test
	void testMakeCopyBlock() {
		assertEquals(nb1.getLocation(), nb1.makeCopyBlock(0).getLocation());
		assertEquals(sb1.getHits()+1, sb1.makeCopyBlock(1).getHits());
		assertEquals(pbb1.getLocation(), pbb1.makeCopyBlock(0).getLocation());
		assertEquals(rb1.getHits(), rb1.makeCopyBlock(0).getHits());
		assertEquals(nb1.makeCopyBlock(0).getHits(), nb1.getHits());
	}
	
	@Test
	void testMakeSuper(){
		assertEquals(ball1.getLocation(), nb1.makeSuper(ball1).getLocation());
		assertEquals(sball1.getLocation().getCenter().getX(), pbb1.makeSuper(ball1).getLocation().getCenter().getX());
	}
	
	@Test
	void testGetColor(){
		assertEquals(Color.green, pbb1.getColor());
		assertEquals(Color.cyan, nb1.getColor());
		assertEquals(Color.red, sb1.getColor());
		assertEquals(Color.pink, sb2.getColor());
		assertEquals(Color.MAGENTA, sb0.getColor());
		assertEquals(Color.MAGENTA, sb3.getColor());	
	}
	

}
