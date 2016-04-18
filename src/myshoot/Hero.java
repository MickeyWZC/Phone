package myshoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Hero {
	private BufferedImage heroImg;
	private int x;
	private int y;
	private boolean live;
	private int life;
	private int score;
	private boolean doubleFire;
	public Hero(BufferedImage heroImg)
	{
		this.heroImg = heroImg;
		x = (int)(Game.backGround.getWidth()-heroImg.getWidth())/2;
		y = 400;
		live = true;
		life = 3;
		score = 0;
		doubleFire = false;
	}
	public void addScore()
	{
		score++;
	}
	public void award()
	{
		if(!this.live) return;
		int x = (int)(Math.random()*5);
		if(x==0) doubleFire = true;
		else if(x==1) life++;
		else {life++;life++;}
	}
	public void setLive(boolean live)
	{
		this.live = live;
	}
	public boolean isLive()
	{
		return live;
	}
	public void setXY(int x,int y)
	{
		this.x = (int)(x - heroImg.getWidth()*0.5);
		this.y = (int)(y - heroImg.getHeight()*0.5);
	}
	public void paint(Graphics g)
	{
		g.drawImage(heroImg, x, y, null);
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void addLife()
	{
		life++;
	}
	public void deLife()
	{
		life--;
		if(life<=0) live = false; 
 	}
	public boolean isDoubleFire()
	{
		return doubleFire;
	}
	public int getLife()
	{
		return life;
	}
	public int getScore()
	{
		return score;
	}
}
