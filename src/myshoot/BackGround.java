package myshoot;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class BackGround{
	private int y1;
	private int y2;
	private BufferedImage bg;
	private int bg_height;
	public BackGround(BufferedImage bg)
	{
		this.bg = bg;
		bg_height = bg.getHeight();
		y1 = -bg_height;
		y2 = 0;
	}
	public void action()
	{
		y1++;
		y2++;
		if(y1==bg_height) y1 = -bg_height;
		if(y2==bg_height) y2 = -bg_height;
	}
	public void paint(Graphics g)
	{
		g.drawImage(bg, 0, y1, null);
		g.drawImage(bg, 0, y2, null);
	}
	/*public static void main(String[] args)
	{
		System.out.println(BackGround.bg.getHeight());
		System.out.println(BackGround.bg.getWidth());
	}*/
}
