package com.workingWithThread.service;

public interface ComputationalService {

    void matrixMultiplication(String strategy, int size, int threadCount, boolean printMatrix);
    void arraySum(String strategy, int size, int threadCount, boolean printArray);
}
