package com.workingWithThread.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class Util {

    private static Logger LOG = LogManager.getLogger(Util.class);
    private static Random random = new Random();
    static {
        random.setSeed(20L);
    }
    public static final int INT_BOUND = 1000;
    public static int[][] getRandomMatrix(int row, int col) {
        int[][] matrix = new int[row][col];
        for(int i = 0 ; i < row ; i++) {
            for(int j = 0 ; j < col ; j++) {
                matrix[i][j] = random.nextInt(INT_BOUND);
            }
        }
        return matrix;
    }

    public static int[] getRandomArray(int size) {
        int[] arr = new int[size];
        for(int i = 0 ; i < size ; i++) {
            arr[i] = random.nextInt(INT_BOUND);
        }
        return arr;
    }

    public static void printMatrix(int[][] matrix) {
        StringBuilder sb = null;
        for (int[] arr : matrix) {
            sb = new StringBuilder();
            for (int i : arr)
                sb.append(i + " ");
            LOG.info(sb.toString());
        }
    }

    public static void printArr(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for(int element : arr) {
            sb.append(element + " ");
        }
        LOG.info(sb.toString());
    }
}
