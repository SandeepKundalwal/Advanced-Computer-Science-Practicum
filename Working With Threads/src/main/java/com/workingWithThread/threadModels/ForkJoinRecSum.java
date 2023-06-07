package com.workingWithThread.threadModels;

import java.util.concurrent.RecursiveTask;

@SuppressWarnings("serial")
public class ForkJoinRecSum extends RecursiveTask<Integer>  {
    public static final int SEQUENTIAL_THRESHOLD = 10000;

    private int lo, hi;
    private int[] arr;

    public ForkJoinRecSum(int[] arr, int lo, int hi) {
        this.lo = lo;
        this.hi = hi;
        this.arr = arr;
    }

    @Override
    public Integer compute() {
        if (hi - lo <= SEQUENTIAL_THRESHOLD) {
            int ans = 0;
            for (int i = lo; i < hi; i++) {
                ans += arr[i];
            }
            return ans;
        } else {
            int mid = (lo + hi) / 2;
            ForkJoinRecSum left = new ForkJoinRecSum(arr, lo, mid);
            ForkJoinRecSum right = new ForkJoinRecSum(arr, mid, hi);
            left.fork();
            int rightAns = right.compute();
            int leftAns = left.join();
            return leftAns + rightAns;
        }
    }
}
