# Senior-Java-Project
java提高篇学习记录

## java提高篇之数组（1）：认识JAVA数组

[学习地址](http://www.importnew.com/20666.html)

* 什么是数组

	数组是一个简单的复合数据类型，它是一系列有序数据的集合，它当中的每一个数据都具有相同的数据类型，我们通过数组名加上一个不会越界下标值来唯一确定数组中的元素。
	
[有关JVM处理Java数组方法的思考](http://developer.51cto.com/art/201001/176671.htm)

		数组array类名：[I
		数组array父类：class java.lang.Object
		数组array2类名：[[I
		数组array3类名：[[[I
		***************
		Object[]:class [Ljava.lang.Object;
		Object[][]:class [[Ljava.lang.Object;
		Object[][][]:class [[[Ljava.lang.Object;

	
普通的java类是以**全限定路径名+类名**来作为自己的唯一标示的，而数组则是以**若干个[+L+数组元素类全限定路径+类**来最为唯一标示的。



