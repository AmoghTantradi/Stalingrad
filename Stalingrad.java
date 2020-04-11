package FinalGame;

import java.awt.Graphics2D;

import Utilities.GDV5;

@SuppressWarnings("serial")
public class Stalingrad extends GDV5 {

	Screen screen;
	
	public Stalingrad() {
		super();

		screen = new Screen();

		this.setSize((int) Constants.screen_width,(int)Constants.screen_height);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		screen.update();

	}

	@Override
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		
		screen.draw(win);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stalingrad cyka = new Stalingrad();
		cyka.start();
	}

}
