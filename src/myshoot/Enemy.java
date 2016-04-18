package myshoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Enemy {
	protected BufferedImage pic;
	protected boolean live;
	protected double x;
	protected double y;
	protected double speed;
	protected int life;
	public Enemy(BufferedImage pic)
	{
		this.pic = pic;
		this.x = Math.random()*(Game.backGround.getWidth()-pic.getWidth()+1);
		this.y = 0-pic.getHeight();
		live = true;
		this.speed = Math.random()*3+1;
		life = 2;
	}
	public boolean isLive()
	{
		return live;
	}
	public  void action()
	{
		y+=speed;
		if(y>=Game.backGround.getHeight()) live =false;
	}
	public void deLife()
	{
		life--;
		if(life<=0) live =false;
	}
	public void setLive(boolean live)
	{
		this.live = live;
	}
	public void paint(Graphics g)
	{
		g.drawImage(pic, (int)x, (int)y, null);
	}
	public boolean crashBullet(Bullet other)
	{
		if(!other.isLive() || !this.live) return false;
		double x1=this.x - Game.bullet.getWidth();
		double x2=this.x + pic.getWidth();
		double y1=this.y - Game.bullet.getHeight();
		double y2=this.y + pic.getHeight();
		return x1<other.getX() && other.getX()<x2 &&
				y1<other.getY() && other.getY()<y2;
	}
	public boolean crashHero(Hero h)
	{
		if(!h.isLive() || !this.live) return false;
		double x1=this.x - Game.heroImg.getWidth();
		double x2=this.x + pic.getWidth();
		double y1=this.y - Game.heroImg.getHeight();
		double y2=this.y + pic.getHeight();
		return x1<h.getX() && h.getX()<x2 &&
				y1<h.getY() && h.getY()<y2;
	}
}
