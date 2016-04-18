package abstractClass;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.concurrent.SynchronousQueue;

import interfaces.Controlled;
/**
 * 抽象类，中文意思：抽象应用
 * 继承了基本面板因为任何程序肯定都是一个面板
 * 实现了可控制接口（用代理的方式实现多继承）
 * @author soft01
 *
 */
public abstract class AbstractApplication extends BasePanel
implements Controlled{
	/**
	 * 为了在抽象类中实现代理
	 * 我在定义的时候将他定义为null
	 * 所以当你们实现了这个抽象类的时候
	 * 记住一定要在初始化的时候给它复制
	 */
	protected ControlledTask app = null;
	
	/**
	 * 代理app.start()方法
	 */
	public final void Start(){
		app.Start();
	}
	/**
	 * 代理app.pause（）方法
	 */
	public final void pause(){
		app.pause();
	}
	/**
	 * 代理app.goOn()方法
	 */
	public final void goOn(){
		app.goOn();
	}
	/**
	 * 代理app.end()方法
	 */
	public final void end(){
		app.end();
	}	
}
