/**
 * 
 */
package com.dyx.sjp.conpro;

/**
 * Author    : Yongxinda(yongxinda89@gmail.com)
 * Version   : 1.0
 * Date      : 2016年7月27日
 * Time      : 下午2:40:12
 * Summary   : 待决中断
 * Copyright : Copyright (c) 2016
 */
public class TestDemo3 extends Object {
	public static void main(String[] args) {
		if (args.length > 0) {
			// 中断当前线程
			Thread.currentThread().interrupt();
		}

		// 获取当前时间
		long currentTime = System.currentTimeMillis();

		try {
			// 休眠2S
			Thread.sleep(2000);
			System.out.println("没有中断");
		} catch (InterruptedException e) {
			System.out.println("中断");
		}

		System.out.println("耗时：" + (System.currentTimeMillis() - currentTime));
	}
}
