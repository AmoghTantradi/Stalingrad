package FinalGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class Healthbar extends Rectangle2D.Double implements GameObject {
	
	double ratio, original;
	
	
	public Healthbar(Tank s) {
		
		
		super((Constants.screen_width - Constants.barLength), (Constants.screen_height*0.95), Constants.barLength, Constants.barWidth);
		
		ratio = (Constants.barLength/(double)s.health);
		
		original = s.health;
		
	}
	
	public void update(Tank s) {
		
		double newX = (ratio*(double)s.health);
		
		this.setRect((Constants.screen_width-newX),this.y,newX,this.getHeight());
		
		
		
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		win.setColor(Color.magenta);
		win.fill(this);
	}
	
	
	
	
	
	
	
	
	

}
