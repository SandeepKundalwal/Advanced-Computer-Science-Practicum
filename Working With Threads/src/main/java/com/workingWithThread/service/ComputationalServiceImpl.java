package com.workingWithThread.service;

import com.workingWithThread.threadModels.ArraySum;
import com.workingWithThread.threadModels.ForkJoinRecSum;
import com.workingWithThread.threadModels.MatrixMultiplicationHV;
import com.workingWithThread.threadModels.MatrixMultiplicationVH;
import com.workingWithThread.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;

public class ComputationalServiceImpl implements ComputationalService {

    private static Logger LOG = LogManager.getLogger(ComputationalServiceImpl.class);
    @Override
    public void matrixMultiplication(String strategy, int size, int threadCount, boolean printMatrix) {
        try {
            int[][] res = null, matrixA, matrixB;
            matrixA = Util.getRandomMatrix(size, size);
            matrixB = Util.getRandomMatrix(size, size);
            long start = System.currentTimeMillis();
            if(strategy.equalsIgnoreCase("HVSplit")) {
                // Split matrixA into horizontal and matrixB into vertical half's.
                res = multiplyMatrixHV(matrixA, matrixB);
            } else if(strategy.equalsIgnoreCase("VHSplit")) {
                // Split matrixA into Vertical and matrixB into Horizontal half's.
                res = multiplyMatrixVH(matrixA, matrixB);
            } else if(strategy.equalsIgnoreCase("DHSplit")) {
                // Split matrixA into horizontal half's equally according to threadCount keeping matrixB constant.
                res = multiplyMatrixDH(matrixA, matrixB, threadCount);
            } else {
                LOG.info("Wrong input parameters passed.");
            }
            long end = System.currentTimeMillis();
            if(printMatrix && res != null) {
                LOG.info("Printing resultant matrix : ");
                Util.printMatrix(res);
            }
            LOG.info("Time taken for Matrix Multiplication : " + (end - start) + "ms");
        } catch (Exception e) {
            LOG.error("ComputationalServiceImpl.matrixMultiplication.Exception: ", e);
        }
    }

    @Override
    public void arraySum(String strategy, int size, int threadCount, boolean printArray) {
        try {
            int[] arr = Util.getRandomArray(size);
            int sum = 0;
            long start = System.currentTimeMillis();
            if(strategy.equalsIgnoreCase("ForkJoinPool")) {
                sum = arrSumForkJoin(arr);
            } else if(strategy.equalsIgnoreCase("ThreadPool")) {
                sum = arrSumThreadPool(arr, threadCount);
            } else if(strategy.equalsIgnoreCase("ManualThread")) {
                sum = arrSumCustomizeThread(arr, threadCount);
            }
            long end = System.currentTimeMillis();
            if(printArray && arr != null) {
                LOG.info("Printing array : ");
                Util.printArr(arr);
            }
            LOG.info("Sum of array : " + sum);
            LOG.info("Time taken for ArraySum : " + (end - start) + "ms");
        } catch (Exception e) {
            LOG.error("ComputationalServiceImpl.arraySum.Exception : ", e);
        }

    }

    public int[][] multiplyMatrixHV(int[][] matrixA, int[][] matrixB) {
        int[][] resMatrix = null;
        try {
            resMatrix = new int[matrixA.length][matrixB[0].length];
            Thread thread1 = new Thread(new MatrixMultiplicationHV(matrixA, matrixB, resMatrix, 0, matrixA.length/2, 0, matrixB[0].length/2));
            Thread thread2 = new Thread(new MatrixMultiplicationHV(matrixA, matrixB, resMatrix, 0, matrixA.length/2, matrixB[0].length/2, matrixB[0].length));
            Thread thread3 = new Thread(new MatrixMultiplicationHV(matrixA, matrixB, resMatrix, matrixA.length/2, matrixA.length, 0, matrixB[0].length/2));
            Thread thread4 = new Thread(new MatrixMultiplicationHV(matrixA, matrixB, resMatrix, matrixA.length/2, matrixA.length, matrixB[0].length/2, matrixB[0].length));
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (Exception e) {
            LOG.error("ComputationalServiceImpl.multiplyMatrixHV.Exception : ", e);
        }
        return resMatrix;
    }
    public int[][] multiplyMatrixDH(int[][] matrixA, int[][] matrixB, int noOfThreads) {
        int[][] resMatrix = null;
        try {
            resMatrix = new int[matrixA.length][matrixB[0].length];

            Thread threadArr[] = new Thread[noOfThreads];
            Thread remainingWorker = null;
            int maxtrixDivision = matrixA.length/noOfThreads, offset = 1, rowStart = 0, rowEnd = 0;
            while(noOfThreads-- > 0) {
                rowEnd = offset * maxtrixDivision;
                threadArr[offset-1] = new Thread(new MatrixMultiplicationHV(matrixA, matrixB, resMatrix, rowStart, rowEnd, 0, matrixB[0].length));
                threadArr[offset-1].start();
                rowStart = rowEnd;
                offset++;
            }

            /* Multiplication for remaining rows. */
            if(rowEnd < matrixA.length) {
                remainingWorker = new Thread(new MatrixMultiplicationHV(matrixA, matrixB, resMatrix, rowEnd, matrixA.length, 0, matrixB[0].length));
                remainingWorker.start();
            }

            // Waiting for worker thread to finish.
            for(Thread t : threadArr) {
                t.join();
            }
            if(remainingWorker != null)
                remainingWorker.join();


        } catch (Exception e) {
            LOG.error("ComputationalServiceImpl.multiplyMatrixDH.Exception : ", e);
        }
        return resMatrix;
    }

    public int[][] multiplyMatrixVH(int[][] matrixA, int[][] matrixB) {
        int[][] resMatrix = null;
        try {
            resMatrix = new int[matrixA.length][matrixB[0].length];
            ReentrantLock[] lockArr = new ReentrantLock[4];
            for(int i = 0 ; i < lockArr.length ; i++) {
                lockArr[i] = new ReentrantLock();
            }
            Thread thread1 = new Thread(new MatrixMultiplicationVH(matrixA, matrixB, resMatrix, lockArr, 0, matrixA.length/2));
            Thread thread2 = new Thread(new MatrixMultiplicationVH(matrixA, matrixB, resMatrix, lockArr, matrixA.length/2, matrixA.length));
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
        } catch (Exception e) {
            LOG.error("ComputationalServiceImpl.multiplyMatrixVH.Exception : ", e);
        }
        return resMatrix;
    }

    private int arrSumForkJoin(int[] arr) {
        ForkJoinPool fjPool = new ForkJoinPool();
        return fjPool.invoke(new ForkJoinRecSum(arr, 0, arr.length));
    }

    private int arrSumThreadPool(int[] arr, int threadCount) {
        int sum = 0;
        try {
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);//creating a pool of 4 threads
            int start = 0, end = 0;
            int subArraySize = arr.length / 4;
            int[] sumArr = new int[4];
            for (int i = 0 ; i < threadCount ; i++) {
                end = (i+1) * subArraySize;
                Runnable worker = new ArraySum(arr, sumArr, start, end,i);
                start = end;
                //calling execute method of ExecutorService
                executor.execute(worker);
            }
            executor.shutdown();//shutdown() will not allow allocating new tasks to threads but will wait till the completion of all allocated tasks

            while (!executor.isTerminated()) { }

            for(int i = 0 ; i < sumArr.length ; i++) {
                sum += sumArr[i];
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    private int arrSumCustomizeThread(int[] arr, int threadCount) {
        int sum = 0;
        try {
            Thread[] threadArr = new Thread[threadCount];
            int[] sumArr = new int[threadCount];
            int start = 0, end = 0;
            int subArraySize = arr.length / 4;

            for (int i = 0 ; i < threadCount ; i++) {
                end = (i+1) * subArraySize;
                threadArr[i] = new Thread(new ArraySum(arr, sumArr, start, end, i));
                threadArr[i].start();
                start = end;
            }

            for(int i = 0 ; i < sumArr.length ; i++) {
                threadArr[i].join();
                sum += sumArr[i];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }
}
