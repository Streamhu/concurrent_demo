package com.example.demo.aqs;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @author huhui
 * @since 2019/1/17 10:26
 */
public class CyclicBarrierTest {

    //定义屏障，指定数量为5个
    private static CyclicBarrier barrier = new CyclicBarrier(5);

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newCachedThreadPool();
        //往线程池中放入线程
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executor.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }

    private static void race(int threadNum) throws Exception {
        Thread.sleep(1000);
        System.out.println(threadNum + " is ready");
        //如果当前线程就绪，则告诉CyclicBarrier 需要等待
        barrier.await();
        // 当达到指定数量时，继续执行下面代码
        System.out.println(threadNum + " continue");
    }

}
