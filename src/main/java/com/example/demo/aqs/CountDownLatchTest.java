package com.example.demo.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author huhui
 * @since 2019/1/17 9:32
 */
public class CountDownLatchTest {

    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception {
        //定义线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        //定义闭锁实例
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            //每次放入一个线程
            exec.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    //计算器完成一次
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        // countDownLatch.await(10, TimeUnit.MILLISECONDS);
        //所有子任务执行完后才会执行
        System.out.println("finish");
        //线程池不再使用需要关闭
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        System.out.println("threadNum" + threadNum);
        Thread.sleep(100);
    }

}
