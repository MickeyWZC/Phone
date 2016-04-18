package test;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import myshoot.Game;
import system.ThreadPool;
import utils.FW;

public class Phone extends JFrame{
	public static  BufferedImage bg;
	public static  Phone phone ;
	public static 	OS os;
	static{
		try {
			bg = ImageIO.read(new File("src/test/start.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private  int width;
	private  int height;
	private  Future<?> last;
	private long t;
	private int x;
	private int y;
	private Phone(int width1, int height1){
		this.width = width1;
		this.height = height1;
		setSize(width,height);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(new BG());
		setLayout(null);
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				os.mouseMoved(e);
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(System.currentTimeMillis() -t>=150)
				os.mouseDragged(e);
			}
		});
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getButton()!=1) return;
				if(System.currentTimeMillis() - t<150&&(e.getX()!=x||e.getY()!=y)) this.mouseClicked(e);
				os.mouseReleased(e);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton()!=1) return;
				t = System.currentTimeMillis();	
				x = e.getX();
				y = e.getY();
				os.mousePressed(e);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				return;
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				return;
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()!=1) return;
				int x= e.getX();
				int y = e.getY();
				if(y<=height-width/6) os.mouseClicked(e);
				else if(FW.circle(width/2, height-width/12, width/6, x, y)){
					int n = e.getClickCount();
					if(n%2==1) last = ThreadPool.THREADPOOL.submit(new homeClick());
					else  last.cancel(true);
				}
			}
		});
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				os.mouseWheelMoved(e);
			}
		});
		
	}
	public static void main(String[] args){
		phone = new Phone(480,680);
		os = new OS(phone.getWidth(),phone.getHeight()-phone.getWidth()/6,new Game());
		addP(os);
		phone.setVisible(true);
	}
	public static void addP(JPanel p){
		phone.add(p, 0);
		phone.repaint();
	}
	public static void removeP(JPanel p){
		phone.remove(p);
		phone.repaint();
	}
	public static void removeAll(JPanel p){
		phone.getContentPane().removeAll();
		phone.add(p,0);
		phone.repaint();
	}
	public static void removeAll(JPanel p,JPanel o){
		phone.getContentPane().removeAll();
		phone.add(p,0);
		phone.add(o,0);
		phone.repaint();
	}
	class BG extends JPanel{
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int width = Phone.this.getWidth();
			int height = Phone.this.getHeight();
			g.drawImage(bg, 0, 0, width, height-width/6, null);
			g.setColor(Color.BLACK);
			g.fillArc(width/2-width/12, height-width/6, 
					width/6, width/6, 0, 360);
		}
	}
}
class homeClick implements Runnable{
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(500);
			Phone.os.homeSingle();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Phone.os.homeDouble();
		}
	}
	
}
