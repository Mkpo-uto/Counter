## This is the README markdown human readable format file to explain the 2nd stage code for working with Threads

Key things to learn from this session
1. A thread has it's own personal memory called thread stack that it has exclusive access to
2. Threads share a common memory for a process called the heap.
3. Local variables are created in the thread stack giving each thread its own copy while class 
or instance variables are created in the heap meaning they are shared by the threads
4. Writing and updating a variable shared in the heap by two or more threads results in Thread interference
and race condition
5. One way to work around race condition will be to declare two different objects and pass them to the two threads
but this is not practical in a real-life application scenario e.g. a bank account object should not receive different
objects to process different operations based on threads as this will degrade the integrity of the account object.
6. To prevent race condition the synchronized key word can be used on a method as well as to include a block of code
allowing a thread to have access to Java class intrinsic lock and perform its operation while other threads wait until
the thread with the lock completes its operation and releases the lock for the next thread scheduled. Local variables 
cannot be used to synchronized block as their references are declared on the thread stack making them exclusive to thread
while their values are stored on the heap. An ideal variable for use in the synchronized code block will be *this* because
threads compete for it aloowing it to handle the race condition

