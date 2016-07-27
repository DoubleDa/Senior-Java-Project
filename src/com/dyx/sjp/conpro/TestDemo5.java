/**
 * 
 */
package com.dyx.sjp.conpro;

/**
 * Author    : Yongxinda(yongxinda89@gmail.com)
 * Version   : 1.0
 * Date      : 2016年7月27日
 * Time      : 下午3:02:51
 * Summary   : 使用Thread.interrupted（）方法判断中断状态
 * Copyright : Copyright (c) 2016
 */
public class TestDemo5 extends Object {
	public static void main(String[] args) {
		System.out.println("A:" + Thread.interrupted());
		// 中断当前线程
		Thread.currentThread().interrupt();

		System.out.println("B:" + Thread.interrupted());
		System.out.println("C:" + Thread.interrupted());
	}
}
