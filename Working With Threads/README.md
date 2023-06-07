# Multithreading in Java

### Argument Format:
- ["Type"  "Strategy" "Size" "Thread_size" "printEnable"] \
 e.g -> [ArraySum ThreadPool 1000000 4] \
 e.g -> [matrixMultiplication HVSplit 5500 8]
- Type -> matrixMultiplication | ArraySum
- Strategies -> 
    - For Matrix Multiplication = HVSplit, VHSplit, DHSplit (HV: Horizontal Split; VH: Vertical Split; DH: Dynamic Horizontal;)
    - For Array sum = ForkJoinPool, ThreadPool, ManualThread
- Preferred values for thread size - [1, 4, 8 32]

### To run the jar file ->
    java -jar script/WorkingWithThreads.jar matrixMultiplication HVSplit 5500 4 false
