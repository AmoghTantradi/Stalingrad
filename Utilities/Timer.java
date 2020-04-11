package Utilities;

import FinalGame.Constants;

//import Snake.GameObject;
//remember to make a gameobject interface class
public class Timer {
	
	
	public int tick;
	public double delay;//delay in seconds
	public boolean isCounting = false;
	
	public Timer(double delay) {
		
		this.delay = delay;
		
	}
	
	public void start() {
		isCounting = true;
	}
	
	public void stop() {
		
		this.isCounting = false;
		
	}
	
	public void restart() {
		
		this.tick=0;
		this.start();
		
	}
	
	public boolean update() {
		
		if(this.isCounting) {
			
			if(this.tick >= this.delay*Constants.FPS) {
				
				this.tick = 0;
			//	this.isCounting = false;
				return true;//it met its delay
				
				
				
				
			} else {
				
				this.tick++;//it hasn't and so it is  still updating
				
				
				
			}
		}
		
		return false;//the delay has not been met
		
		
	}

}
