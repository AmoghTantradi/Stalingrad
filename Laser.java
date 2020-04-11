package FinalGame;

import java.awt.*;
import java.awt.geom.*;

@SuppressWarnings("serial")
public class Laser extends Rectangle2D.Double implements GameObject {
	

	double theta = 0;
	double dx,dy;
	double speed = 10.0f;
	public Laser(Turret t) {

		super(((t.getCenterX() - 5*Math.cos(Math.toRadians(t.theta)))+(t.getCenterX() + 5*Math.cos(Math.toRadians(t.theta))))/2,
				((t.getCenterY() - 5*Math.sin(Math.toRadians(t.theta)))+(t.getCenterY() + 5*Math.sin(Math.toRadians(t.theta))))/2,5,5);
		theta = t.theta;
		dx = speed*Math.sin(Math.toRadians(theta));
		dy = speed*-Math.cos(Math.toRadians(theta));
		
		
	}

	public void update() {
	

		
		super.setRect(this.x+dx, this.y+dy, this.getWidth(), this.getHeight());
		
	}
	
	
	public boolean outBounds() {
		return (this.getCenterX()<0 || this.getCenterX()>Constants.screen_width || this.getCenterY()>Constants.screen_height
				|| this.getCenterY()<0);
	}

	@Override
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		AffineTransform old = win.getTransform();
		win.setColor(Color.red);
		
		win.rotate(Math.toRadians(theta),this.getCenterX(), this.getCenterY());
		
	//	win.drawImage(Screen.rocket, null, this.x, this.y);
	
	
		win.fill(this);
		win.setTransform(old);
		

	}
	
	

}
