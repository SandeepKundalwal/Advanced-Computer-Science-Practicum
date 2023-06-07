package com.workingWithThread.threadModels;


public class MatrixMultiplicationHV implements Runnable {

    int[][] matrixA, matrixB, resMatrix;
    int rowStart, rowEnd, colStart, colEnd;

    public MatrixMultiplicationHV(int[][] matrixA, int[][] matrixB, int[][] resMatrix, int rowStart, int rowEnd, int colStart, int colEnd) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resMatrix = resMatrix;
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
        this.colStart = colStart;
        this.colEnd = colEnd;
    }

    @Override
    public void run() {
        for(int i = rowStart; i < rowEnd; i++) {
            for(int j = colStart; j < colEnd ; j++) {
                for(int k = 0; k < matrixB.length ; k++) {
                    resMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
    }
}
