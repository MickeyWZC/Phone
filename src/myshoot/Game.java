package myshoot;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import abstractClass.AbstractApplication;
import abstractClass.BasePanel;
import abstractClass.ControlledTask;
import interfaces.Controlled;


public class Game extends AbstractApplication{
	private static final int READY = 1;
	private static final int RUNNING = 2;
	private static final int PAUSE = 3;
	private static final int GAMEOVER = 4;
	public static BufferedImage backGround;
	private static BufferedImage airPlane;
	private static BufferedImage bigPlane;
	private static BufferedImage bee;
	public static BufferedImage heroImg;
	public static BufferedImage bullet;
	private static BufferedImage start;
	private static BufferedImage pause;
	private static BufferedImage gameOver;
	static 
	{
		try
		{
			backGround = ImageIO.read(
					new FileInputStream("src/myshoot/background.png"));
			airPlane = ImageIO.read(
					new FileInputStream(
					"src/myshoot/airplane.png"));
			bigPlane = ImageIO.read(
					new FileInputStream(
					"src/myshoot/bigplane.png"));
			bee = ImageIO.read(
					new FileInputStream(
							"src/myshoot/bee.png"));
			bullet = ImageIO.read(
					new FileInputStream(
							"src/myshoot/bullet.png"));
			heroImg = ImageIO.read(
					new FileInputStream(
							"src/myshoot/hero0.png"));
			start = ImageIO.read(
					new FileInputStream(
							"src/myshoot/start.png"));
			pause = ImageIO.read(
					new FileInputStream(
							"src/myshoot/pause.png"));
			gameOver = ImageIO.read(
					new FileInputStream(
							"src/myshoot/gameover.png"));
		}
		catch(Exception e){e.printStackTrace();}
	}
	private int state;
	private int score;
	private int life;
	private BackGround bg;
	private Hero hero;
	private LinkedList<Bullet> bullets;
	private LinkedList<Enemy> enemies;
	private int shoot_index;
	private int enemy_index;
	class Task extends ControlledTask{
		@Override
		protected void endAction() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void action() throws InterruptedException {
			// TODO Auto-generated method stub
			
		bg.action();
			if(!hero.isLive()) state=GAMEOVER;
			if(state==RUNNING)
			{
				shoot_index++;
				enemy_index++;
				if(shoot_index==6) shoot_index=0;
				if(enemy_index==20) enemy_index =0;
				for(Bullet b:bullets)
					b.action();
				for(Enemy e:enemies)
					e.action();
				if(shoot_index ==0) shoot();//发射新子�?				
				if(enemy_index ==0) enemyEnter();//创�?新的对象
				duang();//碰撞�?��
				//actions
			
				for(Iterator<Bullet> it = bullets.iterator();it.hasNext();)
				{
					Bullet b = it.next();
					if(!b.isLive()) it.remove(); 
				}
				for(Iterator<Enemy> it = enemies.iterator();it.hasNext();)
				{
					Enemy e = it.next();
					if(!e.isLive()) 
					{
						hero.addScore();
						if(e instanceof BigPlane||e instanceof Bee) hero.award();
						it.remove();
					}
				}
				//清除死亡对象
				
				
				
			}
			repaint();
			Thread.sleep(1000/30);
			
		}

		@Override
		protected void beforeGoOn() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void beforePause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void initAction() {
			// TODO Auto-generated method stub
			init();
		}
		
	}
	public Game()
	{
		app = new Task();
		init();
	}
	private void init()
	{
		state = READY;
		bg = new BackGround(backGround);
		hero = new Hero(heroImg);
		bullets = new LinkedList<Bullet>();
		enemies = new LinkedList<Enemy>();
		shoot_index = -1;
		enemy_index=-1;
	}
	
	private void duang()
	{
		for(Enemy e:enemies)
		{
			for(Bullet b:bullets)
			{
				if(e.crashBullet(b))
				{
					e.deLife();
					b.setLive(false);
				}
			}
			if(e.crashHero(hero))
			{
				e.setLive(false);
				hero.deLife();
			}
		}
	}
	
	
	private void enemyEnter()
	{
		Random r = new Random();
		int n = r.nextInt(15);
		if(n<=1) enemies.push(new BigPlane(bigPlane));
		else if(n<=2) enemies.push(new Bee(bee));
		enemies.push(new AirPlane(airPlane));
	}
	
	private void shoot()
	{
		int x = hero.getX()+(int)((heroImg.getWidth()-bullet.getWidth())*0.5);
		int y = hero.getY() - bullet.getHeight();
		if(!hero.isDoubleFire())bullets.push(new Bullet(bullet,x,y));
		else {bullets.push(new Bullet(bullet,x-28,y));bullets.push(new Bullet(bullet,x+28,y));}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(state == RUNNING) hero.setXY(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(state == READY)
			state = RUNNING;
		else if(state == GAMEOVER)
				{state = READY;init();}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void yourPaint(Graphics g,int width,int height) {
		// TODO Auto-generated method stub
		bg.paint(g);
		// 绘制敌人 绘制子弹 绘制HERO 
		for(Bullet b :bullets)
				b.paint(g);
		for(Enemy e:enemies)
			e.paint(g);
		hero.paint(g);
		g.drawString(
				"SCORE:"+hero.getScore(), 10, 30);
			g.drawString(
				"LIFE:"+hero.getLife(), 10, 50);
		switch(state)
		{
			case READY:
				g.drawImage(start,0,0,null);break;
			case PAUSE:
				g.drawImage(pause,0,0,null);break;
			case GAMEOVER:
				g.drawImage(gameOver, 0, 0, null);break;
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
	}
	
}
