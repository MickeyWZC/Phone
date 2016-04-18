package myshoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Bullet {
	private BufferedImage bullet;
	private int x;
	private int y;
	private int speed;
	private boolean live;
	public Bullet(BufferedImage bullet,int x,int y)
	{
		speed = -15;
		this.x = x;
		this.y = y;
		this.bullet = bullet;
		live = true;
	}
	public void action()
	{
		this.y+=speed;
		if(y<=0-bullet.getHeight()) live =false;
	}
	public void paint(Graphics g)
	{
		g.drawImage(bullet, x, y, null);
	}
	public boolean isLive()
	{
		return live;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return  y;
	}
	public void setLive(boolean live)
	{
		this.live = live;
	}
}
