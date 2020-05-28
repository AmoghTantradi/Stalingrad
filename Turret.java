package FinalGame;

import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.*;

import Utilities.GDV5;
import Utilities.Timer;

@SuppressWarnings("serial")
public class Turret extends Rectangle2D.Double implements GameObject {
	
	
	private boolean fire = false;
	private double reqTheta;
	private double sizex = 30.0f,sizey = 30.0f;
	private ArrayList<Laser> l;
	private ArrayList<Explosion> e;
	private Timer t;
	private Polygon barrel;
	boolean fired = false;
	double theta = 0;
	double dTheta = 2.0f;
	Color col = Color.lightGray;
	
	public Turret( double x, double y) {
	super(x, y,30,30);
	
	//updatePolygon();
	

	l = new ArrayList<Laser>();
	
	e = new ArrayList<Explosion>();
	

	t = new Timer(1.5);
	
	t.start();
	
	}
	
	public void setSize(double x, double y) {
		sizex = x;
		sizey = y;
		this.setFrame(this.x, this.y,x, y);
	}
	
	public void updatePolygon() {
		int x1 = (int)(this.getCenterX() - 5*Math.cos(Math.toRadians(theta)));
		int x2 = (int)(this.getCenterX() + 5*Math.cos(Math.toRadians(theta)));
		int y1 = (int)(this.getCenterY() - 5*Math.sin(Math.toRadians(theta)));
		int y2 = (int)(this.getCenterY() + 5*Math.sin(Math.toRadians(theta)));
		barrel = new 	Polygon(new int[] {x1,(int)(x1+(25*Math.sin(Math.toRadians(theta)))),(int)(x2+(25*Math.sin(Math.toRadians(theta))))
				, x2},
				new int[] {y1,(int)(y1-(25*Math.cos(Math.toRadians(theta)))),(int)(y2-(25*Math.cos(Math.toRadians(theta)))),y2 }, 4);
	}
	
	@Override
	public void update() {}
	
	
	public void update(ArrayList<Tank> enemyTanks) {
		
		
		//changeOrientation(aim.getPosX(), aim.getPosY());
		if(GDV5.KeysPressed[KeyEvent.VK_LEFT]) {
			theta-=dTheta;
			theta%=360;
		}
		if(GDV5.KeysPressed[KeyEvent.VK_RIGHT]) {
			theta+=dTheta;
			theta%=360;
		}
		if(GDV5.KeysTyped[KeyEvent.VK_UP] ) {
			
			if (!fired) {
				l.add(new Laser(this));
				Stalingrad.sound.play(1);
				fired = true;
				GDV5.KeysTyped[KeyEvent.VK_UP] = false; 
			}
			else {
				
				
				GDV5.KeysTyped[KeyEvent.VK_UP] = false; 
			}
		
			
		}
		
		for(int i = 0 ; i < enemyTanks.size(); i++) {
			
			hitBox(enemyTanks.get(i));
			
			
		}
		
		updateLasers();
		
		removeLaser();
		
		updateExplosions();
		
		if(t.update()) {
			fired = false;
		}
		
		
	}

	public void update(Tank s, ArrayList<Tank> enemyTanks) {
		// TODO Auto-generated method stub
		
		//calculate angle between itself and the snake
		//then shoots only if it is aimed at the snake 
		updateLasers();
		
		removeLaser();
		//check if any lasers are still active

		weaponSystems(s,enemyTanks);	//aims the turret at the snake and does collision handling for all tanks
	
		updateExplosions();
		
	}
	
	private void updateLasers() {

		if(fire) {
			if(t.update()) {
				l.add(new Laser(this));
				Stalingrad.sound.play(1);
				fire = false;//shot is fired, stop shooting 
			}
		}
	
	
	for(int i = 0; i< l.size();i++) {
		l.get(i).update();
	}
	}
	
	public void shiftTurret(double dx, double dy) {
		this.setRect(this.x+dx, this.y+dy,sizex, sizey);
	}
	
	private  void updateExplosions() {
		for(int i = 0; i < e.size();i++) {
			e.get(i).update();
			if(e.get(i).particles.size() == 0) {
				e.remove(i);
				i--;
			}
		}
	}
	
	
	private void removeLaser() {
		for(int i = 0; i< l.size(); i++) {
			
			if(l.get(i).outBounds()) {
				l.remove(i);
				i--;
			}
		}
	}
	
	private void weaponSystems(Tank s, ArrayList<Tank> enemyTanks) {
		
		changeOrientation(s.getCenterX(),s.getCenterY());
		
		for(int i = 0; i < enemyTanks.size(); i++) {
			hitBox(enemyTanks.get(i));
		}
	}
		
		
	
	
	private void changeOrientation(double x, double y) {
		
			
			reqTheta = 0;
			
			double delX = x - this.getCenterX();
			
			double delY = y - this.getCenterY();
			
			reqTheta = Math.toDegrees(Math.atan2(delY, delX)) +90.0f;//this is in degrees 
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

			}else {
			//	System.out.println("theta : "+theta +" ReqTheta: "+ reqTheta);
				fire = true;//if the turret has aimed at the snake, shoot.
			}
			
		
			
			
			
		}
		
	
	private void hitBox(Tank s) {//this will damage the snake 
		
		for(int i = 0; i < l.size(); i++) {
			if(l.get(i).intersects(s)){
				s.doDamage();
				e.add(new Explosion(s.getCenterX(),s.getCenterY()));
				l.remove(i);
				i--;
			}
		}
	}

	@Override
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		
		updatePolygon();
		
		win.setColor(Color.DARK_GRAY);
		
		win.fill(barrel);

		AffineTransform a = win.getTransform();
		win.rotate(Math.toRadians(theta),this.getCenterX(), this.getCenterY());
		win.setColor(col);
		win.fill(this);
		
	
		
		win.setTransform(a);
		
		for(int i = 0; i< l.size();i++) {
			l.get(i).draw(win);
		}
		for(int i = 0;i< e.size();i++) {
			e.get(i).draw(win);
		}
		
	
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
