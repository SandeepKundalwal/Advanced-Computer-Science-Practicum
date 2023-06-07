package com.workingWithThread.threadModels;

public class ArraySum implements Runnable {
    int[] arr, sumArr;
    int start, end, threadId;

    public ArraySum(int[] arr, int[] sumArr,int start, int end, int threadId) {
        this.arr = arr;
        this.sumArr = sumArr;
        this.start = start;
        this.end = end;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        for(int i = start ; i < end ; i++) {
            sumArr[threadId] += arr[i];
        }
    }
}
