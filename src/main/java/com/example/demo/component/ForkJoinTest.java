package com.example.demo.component;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * TODO
 *
 * @author huhui
 * @since 2019/1/17 15:13
 */
public class ForkJoinTest extends RecursiveTask<Integer> {
    //设置分割的阈值
    public static final int threshold = 2;
    private int start;
    private int end;

    public ForkJoinTest(int start, int end) {
        this.start = start;
        this.end = end;
    }

    //任务
    @Override
    protected Integer compute() {
        int sum = 0;

        //如果任务足够小就计算任务
        boolean canCompute = (end - start) <= threshold;
        if (canCompute) {
            //任务足够小的时候，直接计算，不进行分裂计算
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 如果任务大于阈值，就分裂成两个子任务计算
            int middle = (start + end) / 2;

            /**
             * 下面可能会产生递归操作
             */
            //继续分裂任务
            ForkJoinTest leftTask = new ForkJoinTest(start, middle);
            ForkJoinTest rightTask = new ForkJoinTest(middle + 1, end);

            // 执行子任务
            leftTask.fork();
            rightTask.fork();

            // 等待任务执行结束合并其结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            // 合并子任务
            sum = leftResult + rightResult;
        }
        //返回结果
        return sum;
    }

    public static void main(String[] args) {
        // 生成一个池
        ForkJoinPool forkjoinPool = new ForkJoinPool();

        // 生成一个计算任务，计算1+2+3+4
        ForkJoinTest task = new ForkJoinTest(1, 100);

        // 执行一个任务，将任务放入池中，并开始执行，并用Future接收
        Future<Integer> result = forkjoinPool.submit(task);

        try {
            System.out.println("result = " + result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
