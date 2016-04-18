package myshoot;

import java.awt.image.BufferedImage;

public class Bee extends Enemy {

	private int lrSpeed;
	
	public Bee(BufferedImage pic) {
		super(pic);
		lrSpeed = (int)(Math.random()*2);
		if(lrSpeed == 0) lrSpeed = -1;
		speed = 1;
	}
	
	public void action()
	{
		y+=speed;
		if(y>Game.backGround.getHeight()-pic.getHeight()) live=false; 
		x+=lrSpeed;
		if(x<=0||x>=Game.backGround.getWidth()-pic.getWidth()) 
			 lrSpeed = -lrSpeed;
	}
}
