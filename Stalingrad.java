package FinalGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Utilities.GDV5;
import Utilities.SoundDriver;

@SuppressWarnings("serial")
public class Stalingrad extends GDV5 {

	Screen screen;
	BufferedImage i; 
	String [] sounds;
	static SoundDriver sound;
	
	public Stalingrad() {
		super();

		screen = new Screen();
		
		sounds = new String[2];

		this.setSize((int) Constants.screen_width,(int)Constants.screen_height);
		
		this.setTitle("Game");
		
		this.setBackground(Color.white);
		
		i = this.addImage("Images/Background.png");
		
		sounds[0] = "Sounds/MissileSound.wav";
		sounds[1] = "Sounds/ExplosionSound.wav";
		
		sound = new SoundDriver(sounds, this);
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		screen.update();

	}

	@Override
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		
		screen.draw(win,i);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stalingrad cyka = new Stalingrad();
		cyka.start();
	}

}
