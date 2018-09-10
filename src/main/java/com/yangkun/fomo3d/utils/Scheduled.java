package com.yangkun.fomo3d.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Scheduled implements Runnable {

	private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	
	public int i = 0;
	/**
	 * 立即执行线程
	 * @param command
	 */
	public void execute(Runnable command) {
		service.execute(command);
	}

	/**
	 * 按秒延迟执行
	 * @param command
	 * @param delay
	 */
	public void schedule(Runnable command, long delay) {
		service.schedule(command, delay, TimeUnit.SECONDS);
	}

	/**
	 * 按毫秒延迟执行
	 * @param command
	 * @param delay
	 */
	public void scheduleMilli(Runnable command, long delay) {
		service.schedule(command, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * 启动线程
	 */
	public void start() {
		execute(this);
	}

	/**
	 * 停止线程
	 */
	public void stop() {
		service.shutdownNow();
	}

}
