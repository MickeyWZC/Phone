package interfaces;

import java.util.concurrent.ExecutorService;

/**
 * 中文理解：可以控制的
 * @author soft01
 *
 */
public interface Controlled {
	/**
	 * 控制方法
	 */
	public void Start();
	/**
	 * 控制暂停
	 */
	public void pause();
	/**
	 * 控制继续
	 */
	public void goOn();
	/**
	 * 控制结束
	 */
	public void end();
}
