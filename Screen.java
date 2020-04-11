package FinalGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Screen implements GameObject {
	
	ArrayList<Tank> enemyTank, friendlyTank;
	int level = 1;
	int gamestate = 1; //normal screen = 0
	int numTanks = 2;
	public Screen() {
		start();
	}
	
	
	public void updateGame() {
		
	//	if(enemyTank.size() == 0 && friendlyTank.get())

		
	
		
		if(enemyTank.size() == 0) {
			
			numTanks++;
			
			end();
			
			start();
			
			
		}
		
		if(isPlayerDead()) {
			end();
			changeState(0);//put it back to homescreen for now
		}

		
		
		
	}
	
	public void changeState(int newState) {
		
		end();
		
		if(newState == 1) start();
		
		gamestate = newState;
		
	}
	
	public boolean isPlayerDead() {
		
		for(int i = 0; i < friendlyTank.size();i++) {
			
			if(friendlyTank.get(i) instanceof PlayerTank)
				return false;
			
			
			
		}
		
		return true;
	}

	
	public void update() {
		// TODO Auto-generated method stub
	if(gamestate == 1) {	
		
		updateTanks();
		updateGame();
		
	}
		
		
		
		
		
	}
	
	public void updateTanks() {

		for(int i = 0; i< enemyTank.size(); i++) {
			if(i < friendlyTank.size()) enemyTank.get(i).update(friendlyTank.get(i));
			else enemyTank.get(i).update(friendlyTank.get(0));
			if(enemyTank.get(i).isDead()) {
				enemyTank.remove(i);
				if(enemyTank.size() == 0 ) {
					updateGame();
				}
			}
			//make a healthbar for the tank
		}
		
		for(int j = 0; j < friendlyTank.size(); j++) {
			if(!(friendlyTank.get(j) instanceof PlayerTank)) {
				if(j < enemyTank.size()) friendlyTank.get(j).update(enemyTank.get(j));
				else friendlyTank.get(j).update(enemyTank.get(0));
			}
			else 
				friendlyTank.get(j).update(enemyTank);
			 
			if(friendlyTank.get(j).isDead()) {
				if(friendlyTank.get(j) instanceof PlayerTank) {
					updateGame();
				}
				friendlyTank.remove(j);
				
			}
		}
		
		
	}
	
	public void start() {
		enemyTank = new ArrayList<Tank>();
		
		friendlyTank = new ArrayList<Tank>();
		
		for(int i = 0 ; i < numTanks; i ++) {
			enemyTank.add(new Tank());
			
			if(i < numTanks - 1 ) friendlyTank.add(new Tank());
			else friendlyTank.add(new PlayerTank());
			
			friendlyTank.get(i).col = Color.black;
			
		//	friendlyTank.get(i).t.col = Color.red; 
			
			
			
		}
		
	}
	
	public void end() {
		enemyTank = null;
		friendlyTank = null;
	}

	@Override
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		if(gamestate == 1) {
			for(int i = 0; i< enemyTank.size(); i++) {
				enemyTank.get(i).draw(win);
			}
			
			for(int j = 0; j < friendlyTank.size(); j++) {
				friendlyTank.get(j).draw(win);
			}
		}
		
	}

}
