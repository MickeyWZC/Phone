package system;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	public final static ExecutorService THREADPOOL = Executors.newCachedThreadPool();
}
