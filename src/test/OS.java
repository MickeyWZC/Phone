package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.LinkedList;

import abstractClass.AbstractApplication;
import abstractClass.ControlledTask;
import myshoot.Game;
import system.ThreadPool;
import utils.FW;

public class OS extends AbstractApplication{
	//主界面1 运行着程序2 任务总览3
	private AbstractApplication current;
	private AbstractApplication test;
	private AbstractApplication wait;
	private int state;
	private int PWidth;
	private int PHeight;
	private long time = 0;
	private int K = 20;
	private int x0;
	private int y0;
	private long t;
	class Task extends ControlledTask{
		@Override
		protected void endAction() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void action() throws Exception {
			// TODO Auto-generated method stub
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
			
		}
		
	}
	public OS(int width,int height,AbstractApplication test){
		app = new Task();
		setOpaque(false);
		setBounds(0,0,width,height);
		this.test = test ;
		PWidth =width;
		PHeight = height;
		state=1;
	}
	

	@Override
	protected void yourPaint(Graphics g,int width,int height) {
		// TODO Auto-generated method stub
		g.setColor(Color.GREEN);
		g.fillRect(width/10,width/10,width/6 , width/6);		
	}
	public void  homeSingle(){
		switch(state){
		case 1:
			break;
		case 2:
			current = null; test.pause();wait = test;this.setBounds(0,0,PWidth,PHeight);break;
		case 3:
			this.setBounds(0,0,PWidth,PHeight);
			test.setBounds(0,0,PWidth,PHeight);
			break;
		}
		state=1;
		Phone.removeAll(this);
	}
	public void homeDouble(){
		switch(state){
		case 3:
			homeSingle();break;
		case 2:
			state = 3;
			current.pause();
			current =null;
			wait =test;
			wait.setBounds(PWidth/4, PHeight/4, PWidth/2, PHeight/2);
			this.setBounds(-PWidth/4-PWidth/K, PHeight/4, PWidth/2, PHeight/2);
			Phone.removeAll(this,test);
			break;
		case 1:
			state = 3;
			if(wait!=null){
				wait.setBounds(PWidth/4, PHeight/4, PWidth/2, PHeight/2);
				this.setBounds(-PWidth/4-PWidth/K, PHeight/4, PWidth/2, PHeight/2);
				Phone.removeAll(this,test);
			}else{
				this.setBounds(PWidth/4, PHeight/4, PWidth/2, PHeight/2);
				Phone.removeAll(this);
			}
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		switch(state){
		case 1:
			if(FW.rec(PWidth/10,PWidth/10,PWidth/6 , PWidth/6,x,y)){
			current = test;
			if(wait!=null){current.goOn();}
			else {current.Start();test.setBounds(0,0,PWidth,PHeight);}
			Phone.addP(current);wait=null;state=2;}
			break;
		case 2 :
			current.mouseClicked(e);break;
		case 3:
			if(wait==null&&
			FW.rec(PWidth/4, PHeight/4, PWidth/2, PHeight/2, x, y)
			){
				this.state  = 1;
				this.setBounds(0,0,PWidth,PHeight);
				Phone.removeAll(this);
			}
			else if(wait!=null&&FW.rec(PWidth/4, PHeight/4, PWidth/2, PHeight/2, x, y)){
				this.state = 2;
				wait = null;
				current = test;
				current.setBounds(0,0,PWidth,PHeight);
				this.setBounds(0,0,PWidth,PHeight);
				Phone.removeAll(this, current);
				current.goOn();
			}else if(wait!=null&&FW.rec(-PWidth/4-PWidth/K, PHeight/4, PWidth/2, PHeight/2, x, y)){
				this.state = 1;
				this.setBounds(0,0,PWidth,PHeight);
				Phone.removeAll(this);
			}break;
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(state==3){
			x0 = e.getX();
			y0 = e.getY();
			t = System.currentTimeMillis();
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(state==2) test.mouseReleased(e);
		if(state==3) {
			int y = test.getY();
			long now  = System.currentTimeMillis();
			if(y<PHeight/4&&(y<0||(y-PHeight/4)/(now-t)>0.2)){
				ThreadPool.THREADPOOL.execute(new MoveTask(true));
			}else{
				ThreadPool.THREADPOOL.execute(new MoveTask(false));
			}
		}
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(state==2) test.mouseDragged(e);
		else if(state==3){
			int x =e.getX();
			int y = e.getY();
			if(Math.abs(x-x0)<50&&y<y0){
				test.setBounds(PWidth/4, PHeight/4+y-y0, PWidth/2, PHeight/2);
			}
		}
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(state==2) test.mouseMoved(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		if(state==2) test.mouseWheelMoved(e);
	}
	class MoveTask implements Runnable{
		private boolean flag;
		public MoveTask(boolean flag){
			this.flag = flag;
		}
		@Override
		public void run() {
			if(flag){
				while(test.getY()+test.getHeight()>-10){
				test.setBounds(test.getX(), test.getY()-10,
						test.getWidth(), test.getHeight());
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				test.goOn();
				test.end();
				current = null;
				wait = null ; 
				Phone.removeP(test);
			}else{
				while(Math.abs(test.getY()-PHeight/4)>6){
					test.setBounds(test.getX(), test.getY()+10,
							test.getWidth(), test.getHeight());
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				test.setBounds(PWidth/4, PHeight/4, PWidth/2, PHeight/2);
			}
		}
		
	}
}
