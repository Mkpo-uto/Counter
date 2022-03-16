/*
 * This thread classes project is to attempt to show other ways to work
 * with threads besides just printing messages which includes accepting 
 * variables and analyzing the bugs that can arise from working with 
 * threads and how to work around them. Create the threads in the main 
 * method and give each one of them a name and pass the Countdown object and 
 * start the thread. The heap is the application memory that all threads share.
 * Every thread has a thread stack and that's memory that only that thread can
 * access. Local variables are stored in a thread stack that means each thread has its own copy of a local variable in contrast
 * the memory required to store an objects instance variable is allocated on the heap. 
 * In other words when multiple threads are working with the same object they infact share
 * the same object and don't have their own copy as such so when one thread changes the
 * value of the object instance variable the other threads will see the new value from
 * that point forward. When problems arise because threads are interweaving that situation
 * is known as thread interference. This situation is also referred to as a race condition
 * which is the more common term when two or more threads are writing to a shared resource.
 * There is not a problem if the two threads are only reading the resource, the problem arises
 * when one or more threads is writing to or modifying/updating the resource. Not there are 
 * multiple suspension points with thread interweaving. 
 * .
 */

public class Main {

	public static void main(String[] args) {
		Countdown countdown = new Countdown();
		
		CountdownThread t1 = new CountdownThread(countdown);
		t1.setName("Thread 1");
		CountdownThread t2 = new CountdownThread(countdown);
		t2.setName("Thread 2");
		
		t1.start();
		t2.start();

	}

}
/*
 * When the method in this class is called the code counts down from
 * 10 to 1 at which point it exits. We use the color input from the
 * ThreadColor class to switch the color, for Thread 1 it will print out
 * the thread color in cyan.On the second thread call t2.start() it will
 * print the thread in purple. The process of controlling when a thread executes 
 * code and therefore can access the heap is called synchronization. We can
 * synchronize methods and statements. When a method is synchronized only one thread
 * can access it at a time. When a thread is executing a synchronized method all other
 * threads that want to access the method at that time will be suspended until the 
 * thread running the method exits it, then another thread can run the synchronized 
 * method then another etc. If a class has three synchronized methods only one of 
 * this methods can ever run at a time and only on one thread. Since only one thread
 * can ever run a synchronized method at a time thread can't interweave so it prevents
 * thread interference within the synchronized methods but not outside of those methods.
 * So if the same instance variable is referenced within a synchronized method and outside it
 * where code can make modifications to it we may still see interference. When working with
 * threads we have to synchronize all areas where we think interference can happen. By adding
 * the *synchronized* keyword to the method definition we are stating that we want the method 
 * to finish executing before another thread can access it. So thread 1 gets to run for the no
 * of times the for loop runs and exits and then thread 2 gets to run. In this scenario there is
 * no thread interference as only one of the threads gets to execute the doCountdown method at
 * a time. Unfortunately we can't synchronize constructors and it wouldn't make sense if we could
 * do that. Only one thread can construct an instance and until the instance is constructed it wont
 * be available for use anyway.This one way to prevent a race condition. The second way to prevent a
 * race condition is to synchronize a block of statements rather than an entire method. Every java object
 * has an intrinsic block and we can also see this reference to as a monitor. We can synchronize a block
 * of statements that work with an object by forcing threads to acquire the objects lock before they execute
 * the statement block. Now only one thread can hold the lock at a time so other threads that want the lock 
 * will be suspended until the running thread releases it.Only one of the waiting threads can get the lock
 * and continue executing. In the doCountdown method below the for loop needs to be synchronized as other
 * statement blocks in the method are not affected by thread interference as the switch statement switches
 * values of the current thread passed into it. We can't use the i variable to synchronize the for loop as 
 * primitive types do not have intrinsic locks. When you want to synchronize a variable you cannot use a local
 * variable because they are stored in the thread stack with each thread creating its own copy of the local variable
 * and each copy is an object that has its own lock. Thread one gets the lock for its own copy and continues executing and thread2
 * gets the lock for its own copy and continues executing. When we want to synchronize using an object we have to use an object that
 * the threads will share so that they are both competing for the same lock, using local variables a s a result wont work. The only
 * exception to this is using a string variable reason being string objects are reused within the JVM. The JVM uses string pools for 
 * allocation of string objects. *N.B:* Do not use local variables to synchronize. When using a local object instance the object variable
 * is stored in the thread stack but the object values are stored on the heap. Since the threads will create their own copies of the object
 * the object references will be different they wont be any interference even though the object values are in the heap.Essentially the thread
 * stack will only ever contain primitive values and object references and function references. You can synchronize static methods and use static objects
 * when you do that the lock that is used is owned by the class object associated with the objects class. Synchronization is reentrant that is if a thread
 * has an objects lock and it calls a method to be executed that needs that objects lock it can keep executing. A thread can acquire a lock it already
 * owns. Critical section refers to the code that is referencing a shared resource like a variable. Only one thread at a time should be able to execute a
 * critical section.Thread safe is a class or method that all its critical section within the code has been synchronized so there is no worry about thread
 * interference. A method that isn't thread hasn't been synchronized.                                   
 */
class Countdown{
	private int i;
	public void doCountdown() {
		String color;
		switch(Thread.currentThread().getName()) {
		case "Thread 1":
			color = ThreadColor.ANSI_CYAN;
			break;
		case "Thread 2":
			color = ThreadColor.ANSI_PURPLE;
			break;
			default:
				color = ThreadColor.ANSI_GREEN;
		}
		
		//synchronize with the countdown object that the threads share
		synchronized(this) {
		for(i=10; i>0; i--) {
			System.out.println(color + Thread.currentThread().getName() + ": i =" + i);
		}
		}
	}
}

/*
 * The CountdownThread class instance is started for the run method to
 * call the doCountdown thread method in the Countdown class so both 
 * instances of this thread will count down from 10 to 1  
 */
class CountdownThread extends Thread {
	private Countdown threadCountdown;
	
	public CountdownThread(Countdown countdown) {
		threadCountdown = countdown;
	}
	
	public void run() {
		threadCountdown.doCountdown();
	}
}