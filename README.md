## This is the README markdown human readable format file to explain the 2nd stage code for working with Threads

Key things to learn from this session
1. A thread has it's own personal memory called thread stack that it has exclusive access to
2. Threads share a common memory for a process called the heap.
3. Local variables are created in the thread stack giving each thread its own copy while class 
or instance variables are created in the heap meaning they are shared by the threads
4. Writing and updating a variable shared in the heap by two or more threads results in Thread interference
and race condition

