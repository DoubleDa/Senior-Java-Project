/**
 * 
 */
package com.dyx.sjp.conpro;

/**
 * Author    : Yongxinda(yongxinda89@gmail.com)
 * Version   : 1.0
 * Date      : 2016年7月27日
 * Time      : 下午2:53:16
 * Summary   : TODO
 * Copyright : Copyright (c) 2016
 */
public class TestDemo4 extends Object {
	public static void main(String[] args) {
		// 获取当前线程
		Thread t = Thread.currentThread();
		System.out.println("A:" + t.isInterrupted());

		// 中断
		t.interrupt();
		System.out.println("B:" + t.isInterrupted());
		System.out.println("C:" + t.isInterrupted());

		try {
			// 主线程休眠2s
			Thread.sleep(2000);
			System.out.println("没有中断");
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("中断");
		}
		System.out.println("D:" + t.isInterrupted());
	}
}
