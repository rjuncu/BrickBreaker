package breakout.gui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import breakout.*;
import breakout.Point;

@SuppressWarnings("serial")
public class GameView extends JPanel {
	public static final int ballMoveDelayMillis = 20;

	public BreakoutState breakoutState;
	private Timer ballTimer;
	private boolean leftKeyDown = false;
	private boolean rightKeyDown = false;

	long prevTimestamp = 0;
	
	private BreakoutFacade facade;

	private void gameChanged() {
		repaint(10);
	}

	private void startMovingBalls() {
		ballTimer = new Timer(ballMoveDelayMillis, actionEvent -> {
			long timestamp = System.currentTimeMillis();
			moveBalls(timestamp);
		});
		ballTimer.start();
	}

	/**
	 * Create a new GameView for playing breakout starting from a given
	 * breakoutState.
	 * 
	 * @param breakoutState initial state for the game.
	 */
	public GameView(BreakoutState breakoutState) {
		this.breakoutState = breakoutState;
		this.facade = new BreakoutFacade();

		setBackground(Color.black);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT -> {
					rightKeyDown = true;
					break;
				}
				case KeyEvent.VK_LEFT -> {
					leftKeyDown = true;
					break;
				}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT -> {
					rightKeyDown = false;
					break;
				}
				case KeyEvent.VK_LEFT -> {
					leftKeyDown = false;
					break;
				}
				}
			}
		});
		startMovingBalls();
	}

	private void moveBalls(long timestamp) {
		if (prevTimestamp != 0) {
			int elapsedTime = (int) (timestamp - prevTimestamp);
			// very high elapsed times (for example during debugging) are annoying.
			elapsedTime = Math.min(elapsedTime, BreakoutState.MAX_ELAPSED_TIME);

			int curPaddleDir = 0;
			if (leftKeyDown && !rightKeyDown) {
				breakoutState.movePaddleLeft(elapsedTime);
				curPaddleDir = -1;
			}
			if (!leftKeyDown && rightKeyDown) {
				breakoutState.movePaddleRight(elapsedTime);
				curPaddleDir = 1;
			}
			breakoutState.tick(curPaddleDir, elapsedTime);
			if (breakoutState.isDead()) {
				JOptionPane.showMessageDialog(this, "Game over :-(");
				System.exit(0);
			}
			if (breakoutState.isWon()) {
				JOptionPane.showMessageDialog(this, "Gewonnen!");
				System.exit(0);
			}
			gameChanged();
		}
		prevTimestamp = timestamp;
	}

	@Override
	public Dimension getPreferredSize() {
		Point size = toGUICoord(breakoutState.getBottomRight().plus(new Vector(200, 200)));
		return new Dimension(size.getX(), size.getY());
	}

	@Override
	public boolean isFocusable() {
		return true;
	}

	// Convert point in the game coordinate system to the GUI coordinate system.
	private Point toGUICoord(Point loc) {
		return new Point(loc.getX() / 50, loc.getY() / 50).plus(new Vector(5, 5));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Point topRight = toGUICoord(breakoutState.getBottomRight());
		g.setColor(Color.black);
		g.drawRect(0, 0, topRight.getX(), topRight.getY());

		paintBlocks(g);
		paintBalls(g);
		paintPaddle(g);
		
		// domi: this fixes a visual latency bug on my system...
		Toolkit.getDefaultToolkit().sync();
	}

	private void paintPaddle(Graphics g) {
		// paddle
		PaddleState paddle = breakoutState.getPaddle();
		Rect loc = facade.getLocation(paddle);
		Color c = facade.getColor(paddle);
		Point tl = loc.getTopLeft();
		Point br = loc.getBottomRight();
		g.setColor(c);
		paintPaddle(g, tl, br);
	}

	private void paintPaddle(Graphics g, Point tlg, Point brg) {
		Point tl = toGUICoord(tlg);
		Point br = toGUICoord(brg);
		g.fillRect(tl.getX(), tl.getY(), br.getX() - tl.getX(), br.getY() - tl.getY());
	}

	private void paintBalls(Graphics g) {
		// ball
		for (Ball ball : facade.getBalls(breakoutState)) {
			Point center = facade.getCenter(ball);
			int diam = facade.getDiameter(ball);
			int radius = diam/2;
			Point tl = center.plus(new Vector(-radius,-radius));
			Point br = center.plus(new Vector(radius,radius));
			Color color = facade.getColor(ball);
			paintBall(g, color, tl, br);
		}
	}

	private void paintBall(Graphics g, Color color, Point tlg, Point brg) {
		g.setColor(color);
		Point tl = toGUICoord(tlg);
		Point br = toGUICoord(brg);
		g.fillOval(tl.getX(), tl.getY(), br.getX() - tl.getX(), br.getY() - tl.getY());
	}

	private void paintBlock(Graphics g, Point tlg, Point brg) {
		Point tl = toGUICoord(tlg);
		Point br = toGUICoord(brg);
		g.fillRect(tl.getX(), tl.getY(), br.getX() - tl.getX(), br.getY() - tl.getY());
	}

	private void paintBlocks(Graphics g) {
		// blocks
		for (BlockState block : breakoutState.getBlocks()) {
			g.setColor(facade.getColor(block));
			Rect loc = facade.getLocation(block);
			Point tl = loc.getTopLeft();
			Point br = loc.getBottomRight();
			paintBlock(g, tl, br);
		}
	}

}
