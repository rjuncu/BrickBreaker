package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlockTest {
	int hm1;
	int h1;
	
	Point p11;
	Point p05;
	Point p38;
	Point pm14;
	
	Rect r1138;
	Rect rm1438;
	
	PaddleState pp11;
	ReplicatorPaddleState rpp11;
	
	NormalBall ball1;
	SuperChargedBall sball1;
	
	NormalBlockState nb1;
	SturdyBlockState sb1;
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
		r1138 = new Rect(p11,p38);
		rm1438 = new Rect(pm14,p38);
		pp11 = new PaddleState(p11);
		rpp11 = new ReplicatorPaddleState(p11, 0);
		nb1 = new NormalBlockState(r1138, hm1);
		sb1 = new SturdyBlockState(r1138, h1);
		pbb1 = new PowerupBallBlockState(rm1438, hm1);
		rb1 = new ReplicatorBlockState(rm1438, hm1);
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
	}
	
	@Test
	void testMakeSuper(){
		assertEquals(ball1, nb1.makeSuper(ball1));
		assertEquals(sball1, pbb1.makeSuper(ball1));
	}

}
