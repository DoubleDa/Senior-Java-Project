# Java并发编程

## [Java并发编程(1)：可重入内置锁](http://www.importnew.com/20487.html)

每个Java对象都可以用做一个实现同步的锁，这些锁被称为**内置锁**或**监视器锁**。线程在进入同步代码块之前会自动获取锁，并且在退出同步代码块时会自动释放锁。获得内置锁的唯一途径就是**进入由这个锁保护的同步代码块或方法**。

当某个线程请求一个由其他线程持有的锁时，发出请求的线程就会阻塞。然而，由于内置锁是可重入的，因此如果某个线程试图获得一个已经由它自己持有的锁，那么这个请求就会成功。**“重入”意味着获取锁的操作的粒度是“线程”，而不是调用**。**重入的一种实现方法是，为每个锁关联一个获取计数值和一个所有者线程**。当**计数值为0时**这个锁就被认为是没有被任何线程所持有，当线程请求一个未被持有的锁时，JVM将记下锁的持有者，并且将获取计数值置为1，如果同一个线程再次获取这个锁，计数值将递增，而当线程退出同步代码块时，计数器会相应地递减。当计数值为0时，这个锁将被释放。

重入进一步提升了**加锁行为的封装性**，因此简化了面向对象并发代码的开发。分析如下程序：

		public class Father
		{
    		public synchronized void doSomething(){
        	......
    		}
		}
 
		public class Child extends Father
		{
    		public synchronized void doSomething(){
        	......
        	super.doSomething();
    		}
		}
		
子类覆写了父类的同步方法，然后调用父类中的方法，**此时如果没有可重入的锁，那么这段代码件产生死锁**。

由于Fither和Child中的doSomething方法都是synchronized方法，因此每个doSomething方法在执行前都会获取Child对象实例上的锁。如果内置锁不是可重入的，那么在调用super.doSomething时将无法获得该Child对象上的互斥锁，因为这个锁已经被持有，从而线程会永远阻塞下去，一直在等待一个永远也无法获取的锁。重入则避免了这种死锁情况的发生。

同一个线程在调用本类中其他synchronized方法/块或父类中的synchronized方法/块时，都不会阻碍该线程地执行，因为**互斥锁时可重入**的。

## [Java并发编程（2）：线程中断](http://www.importnew.com/20527.html)

* 使用interrupt（）中断线程

	当一个线程运行时，另一个线程可以调用对应的Thread对象的interrupt（）方法来中断它，该方法**只是在目标线程中设置一个标志**，表示它已经被中断，并立即返回。这里需要注意的是，如果只是单纯的调用interrupt（）方法，线程并没有实际被中断，会继续往下执行。

		代码详见：com.dyx.sjp.conpro.TestDemo2

* 待决中断

	在上面的例子中，sleep（）方法的实现检查到休眠线程被中断，它会相当友好地终止线程，并抛出InterruptedException异常。另外一种情况，如果线程在调用sleep（）方法前被中断，那么该中断称为待决中断，它会在刚调用sleep（）方法时，立即抛出InterruptedException异常。
	
		代码详见：com.dyx.sjp.conpro.TestDemo3
	
	这种模式下，main线程中断它自身。除了将中断标志（它是Thread的内部标志）设置为true外，没有其他任何影响。线程被中断了，但main线程仍然运行，main线程继续监视实时时钟，并进入try块，一旦调用sleep（）方法，它就会注意到待决中断的存在，并抛出InterruptException。于是执行跳转到catch块，并打印出线程被中断的信息。最后，计算并打印出时间差。

* 使用isInterrupted（）方法判断中断状态

	可以在Thread对象上调用isInterrupted（）方法来检查任何线程的中断状态。这里需要注意：**线程一旦被中断，isInterrupted（）方法便会返回true，而一旦sleep（）方法抛出异常，它将清空中断标志，此时isInterrupted（）方法将返回false。**
	
		代码详见：com.dyx.sjp.conpro.TestDemo4
		
* 使用Thread.interrupted（）方法判断中断状态

	可以使用Thread.interrupted（）方法来检查当前线程的中断状态（并隐式重置为false）。又由于它是静态方法，因此不能在特定的线程上使用，而只能报告调用它的线程的中断状态，如果线程被中断，而且中断状态尚不清楚，那么，这个方法返回true。与isInterrupted（）不同，它将**自动重置中断状态为false**，第二次调用Thread.interrupted（）方法，总是返回false，除非中断了线程。
	
		代码详见：com.dyx.sjp.conpro.TestDemo5
		
* 补充

	这里补充下yield和join方法的使用。
	
    **join方法用线程对象调用**，如果在一个线程A中调用另一个线程B的join方法，线程A将会等待线程B执行完毕后再执行。
    
    **yield可以直接用Thread类调用**，yield让出CPU执行权给同等级的线程，如果没有相同级别的线程在等待CPU的执行权，则该线程继续执行。
    


//TODO http://www.importnew.com/page/1?s=Java%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8B

	
	