/**
 * 
 */
package com.dyx.sjp.conpro;

/**
 * Author    : Yongxinda(yongxinda89@gmail.com)
 * Version   : 1.0
 * Date      : 2016年7月27日
 * Time      : 下午2:17:18
 * Summary   : Java并发编程（2）：线程中断
 * Copyright : Copyright (c) 2016
 */
public class TestDemo2 extends Object implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			System.out.println("run()--休眠10s");
			Thread.sleep(10000);
			System.out.println("run()--线程唤醒");
		} catch (InterruptedException e) {
			System.out.println("run()--休眠中断");
			return;
		}
		System.out.println("run()--正常离开");
	}

	public static void main(String[] args) {
		TestDemo2 mTestDemo2 = new TestDemo2();
		Thread thread = new Thread(mTestDemo2);
		// 启动子线程
		thread.start();

		try {
			// 主线程休眠
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("main()--中断子线程");
		thread.interrupt();

		System.out.println("main()--离开");
	}
}
