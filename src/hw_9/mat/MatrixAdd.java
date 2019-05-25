package hw_9.mat;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;


public class MatrixAdd {
    private static class AddTask extends RecursiveTask<Void> {
        private final static int THRESHOLD = 100;
        private double[][] mat1;
        private double[][] mat2;
        private double[][] result;
        private int xStart;
        private int xEnd;
        private int yStart;
        private int yEnd;

        private AddTask(double[][] mat1, double[][] mat2, double[][] result, int xStart, int xEnd, int yStart, int yEnd) {
            this.mat1 = mat1;
            this.mat2 = mat2;
            this.result = result;
            this.xStart = xStart;
            this.xEnd = xEnd;
            this.yStart = yStart;
            this.yEnd = yEnd;
        }

        @Override
        protected Void compute() {
            if (((xEnd - xStart) < THRESHOLD) || ((yEnd - yStart) < THRESHOLD)) {
                for (int i = xStart; i < xEnd; i++) {
                    for (int j = yStart; j < yEnd; j++) {
                        result[i][j] = mat1[i][j] + mat2[i][j];
                    }
                }
            } else {
                int midX = (xStart + xEnd) / 2;
                int midY = (yStart + yEnd) / 2;

                RecursiveTask<Void> calcTopLeft = new AddTask(mat1, mat2, result, xStart, midX, yStart, midY);
                RecursiveTask<Void> calcTopRight = new AddTask(mat1, mat2, result, midX, xEnd, yStart, midY);
                RecursiveTask<Void> calcBottomLeft = new AddTask(mat1, mat2, result, xStart, midX, midY, yEnd);
                RecursiveTask<Void> calcBottomRight = new AddTask(mat1, mat2, result, midX, xEnd, midY, yEnd);

                calcTopLeft.fork();
                calcTopRight.fork();
                calcBottomLeft.fork();
                calcBottomRight.fork();
            }
            return null;
        }
    }

    static void parallelAddMatrix(double[][] mat1, double[][] mat2) {
        double[][] result = new double[mat1.length][mat2[0].length];
        RecursiveTask<Void> addTask = new AddTask(mat1, mat2, result, 0, mat1.length, 0, mat2[0].length);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(addTask);
    }

    static void addMatrix(double[][] mat1, double[][] mat2) {
        double[][] result = new double[mat1.length][mat2[0].length];
        for (int i = 0; i < mat1.length; i++) {
            for (int j = 0; j < mat1[i].length; j++) {
                result[i][j] = mat1[i][j] + mat2[i][j];
            }
        }
    }

    public static void main(String[] args) {
        int size = 3000;
        double[][] mat1 = new double[size][size];
        double[][] mat2 = new double[size][size];
        for (int i = 0; i < mat1.length; i++) {
            for (int j = 0; j < mat2[i].length; j++) {
                mat1[i][j] = Math.random() * 100;
                mat2[i][j] = Math.random() * 100;
            }
        }
        long time = System.currentTimeMillis();
        parallelAddMatrix(mat1, mat2);
        System.out.println((System.currentTimeMillis() - time) + " msec - parallelAddMatrix()");
        time = System.currentTimeMillis();
        addMatrix(mat1, mat2);
        System.out.println((System.currentTimeMillis() - time) + " msec - addMatrix()");
    }
}