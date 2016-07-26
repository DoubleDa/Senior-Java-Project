# Senior-Java-Project
java提高篇学习记录

## [java提高篇之数组(1):认识JAVA数组](http://www.importnew.com/20666.html)


* 什么是数组

	数组？什么是数组？在我印象中的数组是应该这样的：通过new关键字创建并组装他们，通过使用整形索引值访问它的元素，并且它的尺寸是不可变的！

	但是这只是数组的最表面的东西！深一点？就是这样：**数组是一个简单的复合数据类型，它是一系列有序数据的集合，它当中的每一个数据都具有相同的数据类型，我们通过数组名加上一个不会越界下标值来唯一确定数组中的元素**。

	还有更深的，那就是**数组是一个特殊的对象**！！（对于这个LZ理解的不是很好，对JVM也没有看，所以见解有限）。[有关JVM处理Java数组方法的思考](http://developer.51cto.com/art/201001/176671.htm)
	
	不管在其他语言中数组是什么，在**java中它就是对象**。一个比较特殊的对象。
	
		public class Test {
    		public static void main(String[] args) {
        	int[] array = new int[10];
        	System.out.println("array的父类是：" + array.getClass().getSuperclass());
        	System.out.println("array的类名是：" + array.getClass().getName());
    		}
		}
		-------Output:
		array的父类是：class java.lang.Object
		array的类名是：[I
		

	从上面示例可以看出,数组的**是Object的直接子类**,它属于“第一类对象”，但是它又与普通的java对象存在很大的不同，从它的类名就可以看出：[I，这是什么东东？？在JDK中我就没有找到这个类，话说这个"[I”都不是一个合法标识符。怎么定义成类啊？所以我认为SUM那帮天才肯定对数组的底层肯定做了特殊的处理。
	
	我们再看如下示例：
	
		public class Test {
    		public static void main(String[] args) {
        	int[] array_00 = new int[10];
        	System.out.println("一维数组：" + array_00.getClass().getName());
        	int[][] array_01 = new int[10][10];
        	System.out.println("二维数组：" + array_01.getClass().getName());
 
        	int[][][] array_02 = new int[10][10][10];
        	System.out.println("三维数组：" + array_02.getClass().getName());
    	}
		}
		-----------------Output:
		一维数组：[I
		二维数组：[[I
		三维数组：[[[I
		
	通过这个实例我们知道：**[代表了数组的维度**，一个[表示一维，两个[表示二维。可以简单的说数组的类名由若干个'['和数组元素类型的内部名称组成。不清楚我们再看：
	
		public class Test {
    		public static void main(String[] args) {
        	System.out.println("Object[]:" + Object[].class);
        	System.out.println("Object[][]:" + Object[][].class);
        	System.err.println("Object[][][]:" + Object[][][].class);
        	System.out.println("Object:" + Object.class);
    	}
		}
		---------Output:
		Object[]:class [Ljava.lang.Object;
		Object[][]:class [[Ljava.lang.Object;
		Object[][][]:class [[[Ljava.lang.Object;
		Object:class java.lang.Object
		

	从这个实例我们可以看出数组的“庐山真面目”。同时也可以看出数组和普通的Java类是不同的，普通的java类是以**全限定路径名+类名**来作为自己的唯一标示的，而数组则是以**若干个[+L+数组元素类全限定路径+类**来最为唯一标示的。这个不同也许在某种程度上说明了数组也普通java类在实现上存在很大的区别，也许可以利用这个区别来使得JVM在处理数组和普通java类时作出区分。

     我们暂且不论这个[I是什么东东，是由谁来声明的，怎么声明的（这些我现在也不知道！但是有一点可以确认：这个是在运行时确定的）。先看如下：
     
     	public class Test {
    		public static void main(String[] args) {
        	int[] array = new int[10];
        	Class clazz = array.getClass();   
        	System.out.println(clazz.getDeclaredFields().length);   
        	System.out.println(clazz.getDeclaredMethods().length);   
        	System.out.println(clazz.getDeclaredConstructors().length);   
        	System.out.println(clazz.getDeclaredAnnotations().length);   
        	System.out.println(clazz.getDeclaredClasses().length);   
    		}
		}
		----------------Output：
		0
		0
		0
		0
		0
		
	从这个运行结果可以看出，我们亲爱的**[I**没有**生命任何成员变量**、**成员方法**、**构造函数**、**Annotation**甚至连**length成员变量**这个都没有，它就是一个彻彻底底的空类。没有声明length，那么我们array.length时，编译器怎么不会报错呢？确实，数组的length是一个非常特殊的成员变量。我们知道数组的是Object的直接之类，但是Object是没有length这个成员变量的，那么length应该是数组的成员变量，但是从上面的示例中，我们发现数组根本就没有任何成员变量，这两者不是相互矛盾么？
	
		public class Main {
    		public static void main(String[] args) {
        	int a[] = new int[2];
        	int i = a.length;
    		}
		}
		
	打开class文件，得到main方法的字节码：
	
		0 iconst_2 //将int型常量2压入操作数栈  
    	1 newarray 10 (int)//将2弹出操作数栈，作为长度，创建一个元素类型为int, 维度为1的数组，并将数组的引用压入操作数栈  
    	3 astore_1 //将数组的引用从操作数栈中弹出，保存在索引为1的局部变量(即a)中  
    	4 aload_1 //将索引为1的局部变量(即a)压入操作数栈  
    	5 arraylength //从操作数栈弹出数组引用(即a)，并获取其长度(JVM负责实现如何获取)，并将长度压入操作数栈  
    	6 istore_2 //将数组长度从操作数栈弹出，保存在索引为2的局部变量(即i)中  
    	7 return   //main方法返回
    	
	在这个字节码中我们还是没有看到length这个成员变量，但是看到了这个:arraylength ,这条指令是用来获取数组的长度的，所以说JVM对数组的长度做了特殊的处理，它是通过arraylength这条指令来实现的。
	
* 数组的使用方法

    数组的使用方法无非就是四个步骤：声明数组、分配空间、赋值、处理。

    **声明数组**：就是告诉计算机数组的类型是什么。有两种形式：int[] array、int array[]。

    **分配空间**：告诉计算机需要给该数组分配多少连续的空间，记住是连续的。array = new int[10];

    **赋值**：赋值就是在已经分配的空间里面放入数据。array[0] = 1 、array[1] = 2……其实分配空间和赋值是一起进行的，也就是完成数组的初始化。有如下三种形式：
    
    	int a[] = new int[2];    //默认为0,如果是引用数据类型就为null
        int b[] = new int[] {1,2,3,4,5};    
        int c[] = {1,2,3,4,5};
        
 	**处理**：就是对数组元素进行操作。通过数组名+有效的下标来确认数据。
 	
 
 	
## [java提高篇之数组(2)](http://www.importnew.com/20683.html)

* 性能？请优先考虑数组

	在java中有很多方式来存储一系列数据，而且在操作上面比数组方便的多？但为什么我们还需要使用数组，而不是替代它呢？数组与其他种类的容器之间的区别有三个方面：**效率**、**类型**和**保存基本类型的能力**。**在java中，数组是一种效率最高的存储和随机访问对象引用序列的方式**。

	在项目设计中数组使用的越来越少了，而且它确实是没有List、Set这些集合使用方便，但是在某些方面数组还是存在一些优势的，例如：**速度**，而且**集合类的底层也都是通过数组来实现**的。
	
		--------这是ArrayList的add()------
    	public boolean add(E e) {
    		ensureCapacity(size + 1);  // Increments modCount!!
    		elementData[size++] = e;
    		return true;
    	}
    	
	下面利用数组和list来做一些操作比较:
	
	**1、求和**
	
		Long time1 = System.currentTimeMillis();
        for(int i = 0 ; i < 100000000 ;i++){
            sum += arrays[i%10];
        }
        Long time2 = System.currentTimeMillis();
        System.out.println("数组求和所花费时间：" + (time2 - time1) + "毫秒");
        Long time3 = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            sum  += list.get(i%10);
        }
        Long time4 = System.currentTimeMillis();
        System.out.println("List求和所花费时间：" + (time4 - time3) + "毫秒");
		--------------Output:
		数组求和所花费时间：696毫秒
		List求和所花费时间：3498毫秒
		

	从上面的时间消耗上面来说数组**对于基本类型的求和计算的速度是集合的5倍左右**。其实在list集合中，求和当中有一个致命的动作：list.get(i)。这个动作是进行拆箱动作，Integer对象通过intValue方法自动转换成一个int基本类型，在这里就产生了不必要的性能消耗。

    **所以在性能要求较高的场景中请优先考虑数组。**
    
* 变长数组？

	数组是定长的，一旦初始化声明后是不可改变长度的。这对我们在实际开发中是非常不方便的，聪明的我们肯定是可以找到方法来实现的。就如java不能实现多重继承一样，我们一样可以利用内部类和接口来实现([java提高篇(九)-----实现多重继承](http://www.cnblogs.com/chenssy/p/3389027.html))。

	那么如何来实现变长数组呢？我们可以利用List集合add方法里面的扩容思路来模拟实现。下面是ArrayList的扩容方法:
	
		public void ensureCapacity(int minCapacity) {
        modCount++;  
        int oldCapacity = elementData.length;
        /**
         * 若当前需要的长度超过数组长度时进行扩容处理
         */
        if (minCapacity > oldCapacity) {
            Object oldData[] = elementData;    
            int newCapacity = (oldCapacity * 3) / 2 + 1;    //扩容
            if (newCapacity < minCapacity)
                newCapacity = minCapacity;
            //拷贝数组，生成新的数组
            elementData = Arrays.copyOf(elementData, newCapacity);
        	}
   		}
   		
	这段代码对我们有用的地方就在于if语句后面。它的思路是**将原始数组拷贝到新数组中**，**新数组是原始数组长度的1.5倍**。所以模拟的数组扩容代码如下：
	
		
		public class ArrayUtils {
    		/**
     		* @desc 对数组进行扩容
     		* @param <T>
     		* @param datas 原始数组
     		* @param newLen 扩容大小
     		* @return T[]
     		*/
    		public static <T> T[] expandCapacity(T[] datas,int newLen){
        		newLen = newLen < 0 ? datas.length :datas.length + newLen;   
        		//生成一个新的数组
        		return Arrays.copyOf(datas, newLen);
    		}
 
   			/**
     		* @desc 对数组进行扩容处理，1.5倍
     		* @param <T>
     		* @param datas  原始数组
     		* @return T[]
     		*/
    		public static <T> T[] expandCapacity(T[] datas){
        		int newLen = (datas.length * 3) / 2;      //扩容原始数组的1.5倍
        		//生成一个新的数组
        		return Arrays.copyOf(datas, newLen);
    		}
 
    		/**
     		* @desc 对数组进行扩容处理
     		* @param <T>
     		* @param datas 原始数组
     		* @param mulitiple 扩容的倍数
     		* @return T[]
     		*/
    		public static <T> T[] expandCapacityMul(T[] datas,int mulitiple){
        		mulitiple = mulitiple < 0 ? 1 : mulitiple;
        		int newLen = datas.length * mulitiple;
        		return Arrays.copyOf(datas,newLen );
    			}
			}
			
	通过这种迂回的方式我们可以实现数组的扩容。因此在项目中如果确实需要变长的数据集，数组也是在考虑范围之内的，我们不能因为他是固定长度而排斥他！
	
* 数组复制问题

	以前在做集合拷贝的时候由于集合没有拷贝的方法，所以一个一个的复制是非常麻烦的，所以我就干脆使用List.toArray()方法转换成数组然后再通过Arrays.copyOf拷贝，在转换成集合，个人觉得非常方便，殊不知我已经陷入了其中的陷进！我们知道若数组元素为对象，则数组里面数据是对象引用。
	
		public class Test {
    		public static void main(String[] args) {
        	Person person_01 = new Person("chenssy_01");
 
        	Person[] persons1 = new Person[]{person_01};
        	Person[] persons2 = Arrays.copyOf(persons1,persons1.length);
 
        	System.out.println("数组persons1:");
        	display(persons1);
        	System.out.println("---------------------");
        	System.out.println("数组persons2:");
        	display(persons2);
        	//改变其值
        	persons2[0].setName("chessy_02");
        	System.out.println("------------改变其值后------------");
        	System.out.println("数组persons1:");
        	display(persons1);
        	System.out.println("---------------------");
        	System.out.println("数组persons2:");
        	display(persons2);
    	}
    	public static void display(Person[] persons){
        	for(Person person : persons){
            	System.out.println(person.toString());
        	}
    	}
		}
		-------------Output:
		数组persons1:
		姓名是：chenssy_01
		---------------------
		数组persons2:
		姓名是：chenssy_01
		------------改变其值后------------
		数组persons1:
		姓名是：chessy_02
		---------------------
		数组persons2:
		姓名是：chessy_02
		
	从结果中发现,persons1中的值也发生了改变，这是典型的浅拷贝问题。所以通过**Arrays.copyOf()方法产生的数组是一个浅拷贝**。同时数组的clone()方法也是，集合的clone()方法也是，所以我们在使用拷贝方法的同时一定要注意浅拷贝这问题。
		
	[渐析java的浅拷贝和深拷](http://www.cnblogs.com/chenssy/p/3308489.html)
	
	[使用序列化实现对象的拷](http://www.cnblogs.com/chenssy/p/3382979.html)
	
* 数组转换为List注意地方

	我们经常需要使用到Arrays这个工具的asList()方法将其转换成列表。方便是方便，但是有时候会出现莫名其妙的问题。如下：
	
		public static void main(String[] args) {
        	int[] datas = new int[]{1,2,3,4,5};
        	List list = Arrays.asList(datas);
        	System.out.println(list.size());
    	}
		------------Output:
		1
		
	结果是1,是的你没有看错, 结果就是1。但是为什么会是1而不是5呢？先看asList()的源码：
	
		public static <T> List<T> asList(T... a) {
       		return new ArrayList<T>(a);
    	}
    	
	注意这个参数:T…a，这个参数是一个泛型的变长参数，我们知道**基本数据类型是不可能泛型化的，也是就说8个基本数据类型是不可作为泛型参数的**，但是为什么编译器没有报错呢？这是因为在java中，数组会当做一个对象来处理，它是可以泛型的，所以我们的程序是把一个int型的数组作为了T的类型，所以在转换之后List中就只会存在一个类型为int数组的元素了。所以我们这样的程序System.out.println(datas.equals(list.get(0)));输出结果肯定是true。当然如果将int改为Integer，则长度就会变成5了。
	
	我们在看下面程序：
	
		enum Week{Sum,Mon,Tue,Web,Thu,Fri,Sat}
    	public static void main(String[] args) {
        	Week[] weeks = {Week.Sum,Week.Mon,Week.Tue,Week.Web,Week.Thu,Week.Fri};
        	List<Week> list = Arrays.asList(weeks);
        	list.add(Week.Sat);
    	}
    这个程序非常简单，就是讲一个数组转换成list，然后改变集合中值，但是运行呢？
    
    	Exception in thread "main" java.lang.UnsupportedOperationException
    	at java.util.AbstractList.add(AbstractList.java:131)
    	at java.util.AbstractList.add(AbstractList.java:91)
    	at com.array.Test.main(Test.java:18)
    	
	编译没错，但是运行竟然出现了异常错误！UnsupportedOperationException ，当不支持请求的操作时，就会抛出该异常。从某种程度上来说就是不支持add方法，我们知道这是不可能的！什么原因引起这个异常呢？先看asList()的源代码：
	
		public static <T> List<T> asList(T... a) {
       		return new ArrayList<T>(a);
    	}
    	
	这里是直接返回一个ArrayList对象返回，但是注意这个ArrayList并不是java.util.ArrayList,而是Arrays工具类的一个内之类：
	
		private static class ArrayList<E> extends AbstractList<E>
    	implements RandomAccess, java.io.Serializable{
        private static final long serialVersionUID = -2764017481108945198L;
        private final E[] a;
        ArrayList(E[] array) {
            if (array==null)
                throw new NullPointerException();
        a = array;
    	}
       	/** 省略方法 **/
    	}
    	
 	但是这个内部类并没有提供add()方法，那么查看父类：
 
 		public boolean add(E e) {
    		add(size(), e);
    		return true;
    	}
    	public void add(int index, E element) {
    		throw new UnsupportedOperationException();
    	}
    	
	这里父类仅仅只是提供了方法，方法的具体实现却没有，所以具体的实现需要子类自己来提供，但是非常遗憾

	这个内部类ArrayList并没有提供add的实现方法。在ArrayList中，它主要提供了如下几个方法：

	1、size：元素数量

	2、toArray：转换为数组，实现了数组的浅拷贝。

	3、get：获得指定元素。

	4、contains：是否包含某元素。

	所以综上所述，**asList返回的是一个长度不可变的列表。数组是多长，转换成的列表是多长，我们是无法通过add、remove来增加或者减少其长度的**。
	

## [java提高篇之关键字final](http://www.importnew.com/20800.html)

在程序设计中，我们有时可能希望某些数据是不能够改变的，这个时候final就有用武之地了。final是java的关键字，它所表示的是“**这部分是无法修改的**”。不想被改变的原因有两个：**效率**、**设计**。使用到final的有三种情况：**数据**、**方法**、**类**。

* final数据

	有时候数据的恒定不变是很有用的，它能够减轻系统运行时的负担。对于这些恒定不变的数据我可以叫做“常量”。“常量”主要应用与以下两个地方：

**1、编译期常量，永远不可改变。**

**2、运行期初始化时，我们希望它不会被改变。**

对于编译期常量，它在类加载的过程就已经完成了初始化，所以当类加载完成后是不可更改的，编译期可以将它代入到任何用到它的计算式中，也就是说可以在编译期执行计算式。**当然对于编译期常量，只能使用基本类型，而且必须要在定义时进行初始化**。

有些变量，我们希望它可以根据对象的不同而表现不同，但同时又不希望它被改变，这个时候我们就可以使用运行期常量。**对于运行期常量，它既可是基本数据类型，也可是引用数据类型。基本数据类型不可变的是其内容，而引用数据类型不可变的是其引用，引用所指定的对象内容是可变的。**

这里只阐述一点就是：不要以为某些数据是final就可以在编译期知道其值，通过final_03我们就知道了，在这里是使用随机数对其进行初始化，他要在运行期才能知道其值。

* final方法

所有被final标注的方法都是**不能被继承、更改的**，所以对于final方法使用的**第一个原因就是方法锁定，以防止任何子类来对它的修改**。至于**第二个原因就是效率问题**，鄙人对这个效率问题理解的不是很清楚，在网上摘抄这段话：在java的早期实现中，如果将一个方法指明为final，就是同意编译器将针对该方法的所有调用都转为内嵌调用。当编译器发现一个final方法调用命令时，它会根据自己的谨慎判断，**跳过插入程序代码这种正常的调用方式**而**执行方法调用机制（将参数压入栈，跳至方法代码处执行，然后跳回并清理栈中的参数，处理返回值）**，并且以方法体中的实际代码的副本来代替方法调用。这将消除方法调用的开销。当然，如果一个方法很大，你的程序代码会膨胀，因而可能看不到内嵌所带来的性能上的提高，因为所带来的性能会花费于方法内的时间量而被缩减。

对这段话理解我不是很懂就照搬了，那位java牛人可以解释解释下！！

**父类的final方法是不能被子类所覆盖的，也就是说子类是不能够存在和父类一模一样的方法的。**

* final类

如果某个类用final修改，表明该类是**最终类**，**它不希望也不允许其他来继承它**。在程序设计中处于安全或者其他原因，我们不允许该类存在任何变化，也不希望它有子类，这个时候就可以使用final来修饰该类了。

对于final修饰的类来说，它的成员变量可以为final，也可以为非final。如果定义为final，那么final数据的规则同样适合它。而它的方法则会自动的加上final，因为final类是无法被继承，所以这个是默认的。

* final参数

在实际应用中，我们除了可以用final修饰**成员变量**、**成员方法**、**类**，还可以**修饰参数、若某个参数被final修饰了，则代表了该参数是不可改变的**。

如果在方法中我们修改了该参数，则编译器会提示你：The final local variable i cannot be assigned. It must be blank and not using a compound assignment。

同final修饰参数在内部类中是非常有用的，**在匿名内部类中，为了保持参数的一致性，若所在的方法的形参需要被内部类里面使用时，该形参必须为final**。[详情参看](http://www.cnblogs.com/chenssy/p/3390871.html)

* final与static

final和static在一起使用就会发生神奇的化学反应，**他们同时使用时即可修饰成员变量，也可修饰成员方法**。

对于成员变量，该变量一旦赋值就不能改变，我们称它为“全局常量”。可以通过类名直接访问。

对于成员方法，则是不可继承和改变。可以通过类名直接访问。

## [java提高篇之关键字static](http://www.importnew.com/20579.html)

* static代表着什么

在Java中并不存在全局变量的概念，但是我们可以通过static来实现一个“伪全局”的概念，在Java中static表示“**全局**”或者“**静态**”的意思，用来修饰成员变量和成员方法，当然也可以修饰代码块。

Java把内存分为**栈内存**和**堆内存**，其中**栈内存用来存放一些基本类型的变量、数组和对象的引用**，**堆内存主要存放一些对象**。在JVM加载一个类的时候，若该类存在static修饰的成员变量和成员方法，则会为这些成员变量和成员方法在固定的位置开辟一个**固定大小的内存区域**，有了这些“固定”的特性，那么JVM就可以非常方便地访问他们。同时如果静态的成员变量和成员方法不出作用域的话，它们的句柄都会保持不变。同时static所蕴含“静态”的概念表示着它是不可恢复的，即在那个地方，你修改了，他是不会变回原样的，你清理了，他就不会回来了。

同时**被static修饰的成员变量和成员方法是独立于该类的，它不依赖于某个特定的实例变量，也就是说它被该类的所有实例共享**。所有实例的引用都指向同一个地方，任何一个实例对其的修改都会导致其他实例的变化。

* 怎么使用static

static可以用于修饰成员变量和成员方法，我们将其称之为静态变量和静态方法，直接通过类名来进行访问。

ClassName.propertyName

ClassName.methodName(……)

Static修饰的代码块表示静态代码块，**当JVM装载类的时候，就会执行这块代码，其用处非常大**。（对于代码块的使用这几天介绍，敬请关注）

**2.1、static变量**

static修饰的变量我们称之为静态变量，没有用static修饰的变量称之为实例变量，他们两者的区别是：

静态变量是**随着类加载时被完成初始化的，它在内存中仅有一个，且JVM也只会为它分配一次内存，同时类所有的实例都共享静态变量，可以直接通过类名来访问它**。

但是实例变量则不同，**它是伴随着实例的，每创建一个实例就会产生一个实例变量，它与该实例同生共死**。

所以我们一般在这两种情况下使用静态变量：**对象之间共享数据、访问方便**。

**2.2、static方法**

static修饰的方法我们称之为静态方法，我们通过类名对其进行直接调用。由于他在类加载的时候就存在了，它不依赖于任何实例，所以static方法必须实现，也就是说他不能是抽象方法abstract。

Static方法是类中的一种特殊方法，我们只有在真正需要他们的时候才会将方法声明为static。如Math类的所有方法都是静态static的。

**2.3、static代码块**

被static修饰的代码块，我们称之为静态代码块，**静态代码块会随着类的加载一块执行，而且他可以随意放，可以存在于该了的任何地方。**


* Static的局限

Static确实是存在诸多的作用，但是它也存在一些缺陷。

1、它只能调用static变量。

2、它只能调用static方法。

3、不能以任何形式引用this、super。

4、static变量在定义时必须要进行初始化，且初始化时间要早于非静态变量。

总结：无论是变量，方法，还是代码块，只要用static修饰，就是在类被加载时就已经”**准备好了**”,也就是可以被使用或者已经被执行，都可以脱离对象而执行。反之，如果没有static，则必须要依赖于对象实例。


//TODO http://www.importnew.com/?s=java提高篇


















    	














































	
		

    

    

    	





