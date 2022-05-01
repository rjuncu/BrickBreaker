package breakout;

import java.awt.Color;

public class ReplicatorPaddleState extends PaddleState{

	public ReplicatorPaddleState(Point center, int hits) {
		super(center, hits);
	}
	
	
	@Override
	public int getHits() {
		return this.hits;
	}
	
	
	@Override 
	public PaddleState movePaddle(Point ncenter, Rect internalField, int hits) {
		if(hits == -1) {
			hits = 0;
		}
		return new ReplicatorPaddleState(internalField.minusMargin(PaddleState.WIDTH/2,0).constrain(ncenter), hits);
	}
	
	@Override
	public Color getColor() {
		return Color.green;
	}

}
