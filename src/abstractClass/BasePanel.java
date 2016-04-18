package abstractClass;


import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

/**
 * 抽象类 ，中文理解为：基本的面板
 * 继承了JPanel 实现了事件监听接口
 * 如果在自己的程序中要用到面板就尽量用它来扩展
 * 因为我定义了点东西，可以保证设置宽和高
 * 在你绘制的时候能同步
 * @author soft01
 *
 */
public abstract class BasePanel extends JPanel implements  MouseInputListener ,MouseWheelListener {
	/**
	 * 保证同步
	 */
	@Override
	public final synchronized void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		int width = this.getWidth();
		int height = this.getHeight();
		yourPaint(g,width,height);
		g.dispose();
	}
	/**
	 * 钩子函数
	 * 写你们要绘制的东西
	 * @param g
	 */
	protected abstract void yourPaint(Graphics g,int width,int height);
	/**
	 * 设置面板做标和长宽
	 */
	@Override
	public final synchronized void setBounds(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		super.setBounds(x, y, width, height);
	}
	/**
	 * 不用管，因为我们没有这个事件所以不用监听
	 */
	@Override //没用的事件监听处理所以不让你们重写用
	public final void mouseEntered(MouseEvent e) {
	};
	/**
	 * 不用管，因为我们没有这个事件所以不用监听
	 */
	@Override
	public final void mouseExited(MouseEvent e) {
	};
}
