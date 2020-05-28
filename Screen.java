package FinalGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Utilities.GDV5;

public class Screen implements GameObject {
	
	private ArrayList<Tank> enemyTank, friendlyTank;
	private Healthbar health;
	private int level = 1;
	private int gamestate = 0; //normal screen = 0
	private int numTanks = 2;
	public Screen() {
		start();
	}
	
	
	public void updateGame() {

		if(enemyTank.size() == 0) {
			if(this.level<10) {//was 10
				numTanks++;
				
				this.level++;
				
				end();
				
				start();
			}
			else {//end the game once it finishes level 10
				this.level = 1;
				end();
				changeState(3);//put it back to homescreen for now but make a separate ending page
			}
			
		}
		
		if(isPlayerDead()) {//this does the level handling--sets it back to level 1
			this.level = 1;
			end();
			changeState(2);//put it back to homescreen for now
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
	if (gamestate == 0) {
		
		
		if(GDV5.KeysTyped[KeyEvent.VK_SPACE] && gamestate == 0) {
			changeState(1);
			GDV5.KeysTyped[KeyEvent.VK_SPACE] = false;
		}
		
		
		
		
	}
	else if(gamestate == 1) {	
		
		updateTanks();
		updateGame();
		
		
		if(GDV5.KeysTyped[KeyEvent.VK_ESCAPE]) {
			
			changeState(0);
			GDV5.KeysTyped[KeyEvent.VK_ESCAPE] = false;
		}
		
	}
	else {
		if(GDV5.KeysTyped[KeyEvent.VK_ESCAPE]) {
			
			changeState(0);
			GDV5.KeysTyped[KeyEvent.VK_ESCAPE] = false;
		}
	}
		
		
		
		
		
	}
	
	public void updateTanks() {

		for(int i = 0; i< enemyTank.size(); i++) {
			

				if(i < friendlyTank.size()) {
					if(enemyTank.size() < friendlyTank.size() && i != enemyTank.size() -1) {
						enemyTank.get(i).update(friendlyTank.get(i),friendlyTank);
					}
					else if(enemyTank.size() < friendlyTank.size() && i == enemyTank.size() -1) {
						enemyTank.get(i).update(friendlyTank.get(friendlyTank.size()-1), friendlyTank);
					}
					else { //this logical statement is equal to this --> if enemyTank.size() > = friendlyTank.size()
						enemyTank.get(i).update(friendlyTank.get(i),friendlyTank);
					}
				}
				else enemyTank.get(i).update(friendlyTank.get(friendlyTank.size()-1),friendlyTank);
				if(enemyTank.get(i).isDead()) {
					enemyTank.remove(i);
					i--;
					if(enemyTank.size() == 0 ) {
						updateGame();
					}
				}
				//make a healthbar for the tank
			}
		
		
		for(int j = 0; j < friendlyTank.size(); j++) {
			if(!(friendlyTank.get(j) instanceof PlayerTank)) {
				if(j < enemyTank.size()) friendlyTank.get(j).update(enemyTank.get(j),enemyTank);
				else friendlyTank.get(j).update(enemyTank.get(enemyTank.size() - 1), enemyTank);
			}
			else {
				friendlyTank.get(j).update(enemyTank);
				health.update(friendlyTank.get(friendlyTank.size()-1));
			}
			 
			if(friendlyTank.get(j).isDead()) {
				if(friendlyTank.get(j) instanceof PlayerTank) {
					updateGame();
				}
				friendlyTank.remove(j);
				j--;
				
			}
		}
		
		
	}
	
	public void start() {
		enemyTank = new ArrayList<Tank>();
		
		friendlyTank = new ArrayList<Tank>();
		
		
		for(int i = 0 ; i < numTanks; i ++) {

			enemyTank.add(new Tank(Tank.width +i*(( Constants.screen_width - (2*Tank.width))/(double)(numTanks) + 5.0),Constants.screen_height*0.40));	
			enemyTank.get(i).damage = Constants.enemyShellDamage;
			enemyTank.get(i).health = Constants.enemyTankHealth;
			
			if(i < numTanks - 1 ) { 
			friendlyTank.add(new Tank(Tank.width +i*(( Constants.screen_width - (2*Tank.width))/(double)(numTanks) + 5.0), Constants.screen_height - 2*Tank.length));

			}
			else {
				friendlyTank.add(new PlayerTank(Tank.width +i*(( Constants.screen_width - (2*Tank.width))/(double)(numTanks) + 5.0), Constants.screen_height - 2*Tank.length));
				health = new Healthbar(friendlyTank.get(friendlyTank.size()-1));
			}

			friendlyTank.get(i).col = Color.black;
		}
		
		
		
		
	}
	
	public void end() {
		enemyTank = null;
		friendlyTank = null;
		health = null;
	
	}

	public void draw(Graphics2D win, BufferedImage k) {
		// TODO Auto-generated method stub
		
		
		if(gamestate == 0) {//this is the home page
			
			
			Font font = new Font("TimesNewRoman", Font.BOLD, 150);
			
			Font other1 = new Font("TimesNewRoman",Font.BOLD,75);
			
			Font otherother1 = new Font("TimesNewRoman",Font.BOLD,25);
			
			win.setFont(font);
			
			win.setColor(Color.BLACK);
			
			win.drawString("STALINGRAD",(int)(Constants.screen_width*0.115),(int)(Constants.screen_height*0.25));
			
			win.setFont(other1);
			
			win.drawString("By Amogh", (int)(Constants.screen_width*0.335), (int)(Constants.screen_height*0.5));
			
			win.setFont(otherother1);
			
			win.drawString("Use WASD to move your tank--the black one--and press the forward arrow key to shoot",
					(int)(Constants.screen_width*0.1), (int)(Constants.screen_height*0.75));
			win.drawString("Press \"esc\" to go to the home screen and press the space bar to start playing",
					(int)(Constants.screen_width*0.135), (int)(Constants.screen_height*0.85));
			
			
		}
		else if(gamestate == 1) {//if the game is playing
			win.drawImage(k,null,0,0);
			for(int i = 0; i< enemyTank.size(); i++) {
				enemyTank.get(i).draw(win);
			}
			
			for(int j = 0; j < friendlyTank.size(); j++) {
				friendlyTank.get(j).draw(win);
			}
			
			health.draw(win);
			
			Font font = new Font("TimesNewRoman", Font.BOLD, 50);
			
			win.setFont(font);
			
			win.setColor(Color.RED);
			
			win.drawString("Level: "+this.level,0, (int)(Constants.screen_height*0.975));
			
			if(!friendlyTank.get(friendlyTank.size()-1).t.fired) {
				win.drawString("Ready to Fire", 0, (int)(Constants.screen_height*0.875));
			}
			else {
				win.drawString("Loading...", 0, (int)(Constants.screen_height*0.875));
			}
			
			
		}
		
		else if (gamestate ==  2){
			
			win.setColor(Color.black);
			
			Font font = new Font("TimesNewRoman", Font.BOLD, 175);
			
			win.setFont(font);
			
			win.drawString("You Lose",(int)(Constants.screen_width*0.120),(int)(Constants.screen_height*0.25));
			
			
			
		}
		else {
			win.setColor(Color.black);
			Font font = new Font("TimesNewRoman", Font.BOLD, 175);
			win.setFont(font);
			win.drawString("You Win!",(int)(Constants.screen_width*0.120),(int)(Constants.screen_height*0.25));
			
		}
		
		
		
	}


	@Override
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		
	}

}
