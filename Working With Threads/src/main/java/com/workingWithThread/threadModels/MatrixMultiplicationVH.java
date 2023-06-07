package com.workingWithThread.threadModels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class MatrixMultiplicationVH implements Runnable {

    private static Logger LOG = LogManager.getLogger(MatrixMultiplicationVH.class);
    int[][] matrixA, matrixB, resMatrix;
    int start, end;
    ReentrantLock lockArr[];

    public MatrixMultiplicationVH(int[][] matrixA, int[][] matrixB, int[][] resMatrix, ReentrantLock[] lockArr, int start, int end) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resMatrix = resMatrix;
        this.lockArr = lockArr;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        int region;
        for(int i = 0; i < matrixA.length; i++) {
            for(int j = 0; j < matrixB[0].length ; j++) {
                region = getRegion(i,j);
                for(int k = start; k < end; k++) {
                    LOG.debug(String.format("%s  trying to acquire lock on region %d.%n", Thread.currentThread().getName(), region));
                    lockArr[region].lock();
                    LOG.debug(String.format("%s  acquired lock on region %d.%n", Thread.currentThread().getName(), region));
                    resMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                    lockArr[region].unlock();
                }
            }
        }
    }

    private int getRegion(int i, int j) {
        if(i < resMatrix.length /2 && j < resMatrix.length/2) {
            return 0;
        } else if(i < resMatrix.length/2 && j >= resMatrix.length/2) {
            return 1;
        } else if(i > resMatrix.length /2 && j < resMatrix.length/2) {
            return 2;
        } else {
            return 3;
        }
    }
}
