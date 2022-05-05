package breakout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BreakoutStateTest {
	int hm1;
	int h1;
	
	Point p11;
	Point p05;
	Point p38;
	Point pm14;
	Point ncenter;
	Point pbr;
	Point p600;
	Point ppd;
	
	Vector v45;
	Vector vn56;
	
	Rect r1138;
	Rect rm1438;
	
	PaddleState p1;
	ReplicatorPaddleState rp1;
	ReplicatorPaddleState rp11;
	ReplicatorPaddleState rp12;
	Rect iF;
	Ball b1;
	Ball[] balls;
	
	PaddleState pp11;
	ReplicatorPaddleState rpp11;
	
	NormalBall ball1;
	SuperChargedBall sball1;
	
	BlockState[] blocks;
	NormalBlockState nb1;
	SturdyBlockState sb1;
	SturdyBlockState sb2;
	SturdyBlockState sb0; 
	SturdyBlockState sb3;
	PowerupBallBlockState pbb1;
	ReplicatorBlockState rb1;
	
	BreakoutState game;

	@BeforeEach
	void setUp() throws Exception {
		hm1 = -1; 
		h1 = 1;
		p11 = new Point(1,1);
		ppd = new Point(2500,28125);
		p05 = new Point(0,5);
		p38 = new Point(3,8);
		pbr = new Point(50000,30000);
		pm14 = new Point(-1,4);
		p11 = new Point(1,1);
		p1 = new PaddleState(ppd);
		v45 = new Vector(4,5);
		vn56 = new Vector(-5,6);
		rp1 = new ReplicatorPaddleState(p11, 0);
		rp11 = new ReplicatorPaddleState(p11,1);
		rp12 = new ReplicatorPaddleState(p11,2);
		r1138 = new Rect(p11,p38);
		rm1438 = new Rect(pm14,p38);
		iF = new Rect(new Point(0,0), new Point(50000,30000));
		ncenter = new Point(27550,24375);
		b1 = new NormalBall(new Circle(new Point(700,15000),700), new Vector(4,5));
		balls = new Ball[] {b1};
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
		blocks = new BlockState[] {nb1};
		
		game = new BreakoutState(balls,blocks,pbr,p1);
		
		
	}
	
	@Test
	void testMovePaddleLeft() {
		game.movePaddleLeft(1);
		assertEquals(game.getPaddle().getCenter(), new Point(2450,28125));
	}
	
	@Test
	void testMovePaddleRight() {
		game.movePaddleRight(1);
		assertEquals(game.getPaddle().getCenter(), new Point(2550,28125));
	}

}
