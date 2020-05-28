package FinalGame;


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;


import Utilities.GDV5;

@SuppressWarnings("serial")
public class Tank extends Rectangle2D.Double implements GameObject {
	//tank number 2-3-7
	private double theta= 0;
	private double dTheta = 2.0f;
	private double reqTheta;
	private double speed = 1.75d;
	private double dx = 0,dy = 0;
	private boolean moving = false;
	private double ptx,pty;
	public Turret t;
	int health = Constants.health;
	double damage = Constants.shellDamage;
	static double  width = 20.0d;
	static double length = 40.0d;
	Color col = Color.GRAY;
	
	public Tank() {
		this(Constants.screen_width/2,Constants.screen_height/2);
		
	}
	
	public Tank(double xpos, double ypos) {
		super(xpos,ypos,width,length);
		t = new Turret(this.x,this.getCenterY()-width/2);
		t.setSize(width, width);
	}
	
	public boolean isDead() {
	return (this.health<=0);
}	
	public void doDamage() {
		this.health -= damage;
		Stalingrad.sound.play(1);
	}
	
	
	public void update() {}
	
	public boolean isInside(double dx, double dy) {
		
		double radius = 0.5* Constants.tankDiagnol;//the length of half a diagnol is the maximum radius possible
	
		return (Math.abs(this.getCenterX()+dx-0)>radius && Math.abs(this.getCenterY()+dy-Constants.screen_height*0.35)>radius && Math.abs(this.getCenterX()+dx-Constants.screen_width)>radius && Math.abs(this.getCenterY()+dy-Constants.screen_height)>radius);
		
	}

	public void update(ArrayList<Tank> enemyTanks) {
		
		//this method is for the key-controlled player tank
		dx = 0;
		
		dy = 0;
		
		if(GDV5.KeysPressed[KeyEvent.VK_W]) {
			
			dx = speed*Math.sin(Math.toRadians(theta));
			dy = -speed*Math.cos(Math.toRadians(theta));
			if(!isInside(dx, dy)) {
				dx = 0;
				dy = 0;
			}
			
		}
		if(GDV5.KeysPressed[KeyEvent.VK_S]) {
			dx = -speed*Math.sin(Math.toRadians(theta));
			dy = +speed*Math.cos(Math.toRadians(theta));
			if(!isInside(dx, dy)) {
				dx = 0;
				dy = 0;
			}
			
		}
		
		if(GDV5.KeysPressed[KeyEvent.VK_A]) {
			this.theta-=dTheta;
			this.t.theta -= dTheta;//new change here: the turret moves with the tank as well
			theta%=360;
			this.t.theta%=360;
		}
		if(GDV5.KeysPressed[KeyEvent.VK_D]) {
			this.theta+=dTheta;
			this.t.theta+=dTheta;//new change over here: the turret moves with the tank as well
			theta%=360;
			this.t.theta%=360;
		}
		t.update(enemyTanks);
		this.setRect(this.x+dx, this.y+dy, this.getWidth(),this.getHeight());
		t.shiftTurret(dx, dy);
		
	}
	
	
	public void update(Tank s, ArrayList<Tank>enemyTanks) {//s is the tank its gonna target and in case it misses(0r hits its target) enemyTanks is present for the collision handling
		// TODO Auto-generated method stub;
		
		
		//this method is for the autonomous tanks

		synchronizedMovement();//emulates tank movement better

		
		t.shiftTurret(dx, dy);
		this.setRect(this.x+this.dx,this.y+this.dy,width,length);
		t.update(s,enemyTanks);
	}
	
	@SuppressWarnings("unused")
	private void randomMovement() {//emulates car movement better-not tank movement
	
		 ptx = (Tank.width + Math.random()*(Constants.screen_width - Tank.width));
		 pty = (Tank.width + Math.random()*(Constants.screen_height - Tank.width) );
		
		
		movetoPoint(ptx,pty);
		
		dx = speed*Math.sin(Math.toRadians(theta));
		dy = -speed*Math.cos(Math.toRadians(theta));
	
	}
	
	private void stop() {
		dx = 0;
		dy = 0;
	}
	
	private void synchronizedMovement() {//emultates tank movement perfectly
		
		if(!moving) {
		 ptx = (Math.random()*Constants.screen_width);
		 pty = (Math.random()*Constants.screen_height);
		}
		 if(!this.contains(ptx, pty)) {
			 moving = true;
			 movetoPoint(ptx,pty);//shifts the tank to the required position
			 
			 if(Math.abs(theta - reqTheta)<=dTheta) {

					dx = speed*Math.sin(Math.toRadians(theta));
					dy = -speed*Math.cos(Math.toRadians(theta));
					
					if(!isInside(dx,dy)) {//added this new thing here to keep the autonomous  tanks inside the screen 
						dx = 0;
						dy = 0;
						moving = false;
					}
			 
		 }
		
	}
		 else {
			 stop();
			 moving = false;
			
		 }
		 
		
	}
	
	private void  movetoPoint(double x, double y) {
		
		double delX = x - this.getCenterX();
		
		double delY = y - this.getCenterY();
		
		reqTheta = Math.toDegrees(Math.atan2(delY, delX))+90;
		
		reqTheta+=360;
		
		reqTheta%=360;
		
		
		double angDist1 = reqTheta - this.theta;
		
		double angDist2 = 360.0 - angDist1;
		
		angDist1+=360;
		angDist1%=360;
		
		angDist2+=360;
		angDist2%=360;
		
		
		if (!(theta - reqTheta < Constants.epsilon && theta - reqTheta > -Constants.epsilon)) {
		
		
		//	System.out.println("dist1: "+ angDist1+" dist2: "+ angDist2);
			
			if(Math.abs(angDist1)>=Math.abs(angDist2)) {
				theta -= dTheta;
				theta+=360;
				theta%=360;
			}
			else {
				theta+=dTheta;
				theta+=360;
				theta%=360;
			}

		}
		
		

		
		
	
	}

	@Override
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		AffineTransform old = win.getTransform();
		win.rotate(Math.toRadians(theta),this.getCenterX(),this.getCenterY());
		win.setColor(col);
		win.fill(this);
		win.draw(this);
		win.setTransform(old);
		t.draw(win);
	}

}
