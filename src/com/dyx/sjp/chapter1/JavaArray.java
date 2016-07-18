/**
 * 
 */
package com.dyx.sjp.chapter1;

/**
 * Author    : Yongxinda(yongxinda89@gmail.com)
 * Version   : 1.0
 * Date      : 2016年7月18日
 * Time      : 下午11:40:40
 * Summary   : TODO
 * Copyright : Copyright (c) 2016
 */
public class JavaArray {
	public static void main(String[] args) {
		int[] array = new int[10];
		System.out.println("数组array类名：" + array.getClass().getName());
		System.out.println("数组array父类：" + array.getClass().getSuperclass());

		int[][] array2 = new int[10][10];
		System.out.println("数组array2类名：" + array2.getClass().getName());

		int[][][] array3 = new int[10][10][10];
		System.out.println("数组array3类名：" + array3.getClass().getName());

		System.out.println("***************");

		// 数组的类名＝'['+数组元素类型的内部名称
		System.out.println("Object[]:" + Object[].class);
		System.out.println("Object[][]:" + Object[][].class);
		System.out.println("Object[][][]:" + Object[][][].class);

		System.out.println("***************");
		
		int[] myArray = new int[10];
		Class cla = myArray.getClass();
		System.out.println(cla.getDeclaredFields().length);
		System.out.println(cla.getDeclaredMethods().length);
		System.out.println(cla.getDeclaredConstructors().length);
		System.out.println(cla.getDeclaredAnnotations().length);
		System.out.println(cla.getDeclaredClasses().length);
	}
}
