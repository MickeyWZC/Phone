package abstractClass;

import interfaces.Controlled;
import system.ThreadPool;
import utils.WhenWrong;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
/**
 * 抽象类
 * 实现了Controlled接口和Runnable接口
 * 中文理解：可以控制的任务
 * @author soft01
 *
 */
public abstract class ControlledTask implements Runnable,Controlled{
	/**
	 * 用来控制任务结束要用的
	 */
	private Future<?> future;
	/**
	 * 控制任务暂停的标志位
	 */
	private boolean pauseFlag = false;
	
	/**
	 * 任务的运行体run（），因为我要实现控制
	 * 所以在这个方法的结构上作出了一定限制
	 * 最终要的一点就是不能在里面写出死循环，
	 * 一定次数的循环是可以的
	 */
	@Override
	public final void run() {
		try{
			initAction();
			while(!Thread.interrupted()){
				synchronized (this){
					while(pauseFlag)  {beforePause();wait();beforeGoOn();}
				}
				action();
			}
		}catch(Exception e){
			WhenWrong.doSome(e);
			
		}finally{
			System.out.println(this + "线程结束了！");
			endAction();
		}
	}
	/**
	 * 
	 * 钩子函数，结束线程要做的动作
	 * 注意是结束这个任务要做的动作，
	 * 而不是结束游戏要做的动作
	 */
	protected  abstract  void endAction();
	/**
	 * 
	 * 在线程内要做的动作
	 */
	protected abstract void action() throws Exception;
	/**
	 * 从暂停回到继续前要做的动作
	 */
	protected abstract void beforeGoOn();
	/**
	 * 
	 * 进入暂停前要做的动作
	 */
	protected abstract void beforePause();
	/**
	 * 每次开始任务时要执行的任务
	 * 初始化动作
	 */
	protected abstract void initAction();
	/**
	 * 控制开始，将任务提交给线程池
	 * 返回值交给future
	 */
	@Override
	public final void Start() {
		future = ThreadPool.THREADPOOL.submit(this);
	}
	/**
	 * 暂停任务
	 */
	@Override
	public final void pause() {
		synchronized (this){
			pauseFlag = true;
		}	
	}
	/**
	 * 继续任务
	 */
	@Override
	public final void goOn() {
		synchronized(this){
			pauseFlag = false;
			notifyAll();
		}
	}
	/**
	 * 结束一个任务
	 */
	@Override
	public final void end() {
		future.cancel(true);
	}
}
